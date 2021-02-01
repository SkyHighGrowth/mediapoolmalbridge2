package com.brandmaker.mediapoolmalbridge.service;

import com.brandmaker.mediapoolmalbridge.config.MalPriorities;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.schedule.ServiceEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.MALAssetOperation;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.schedule.ServiceState;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.AssetRepository;
import com.brandmaker.mediapoolmalbridge.tasks.TaskExecutorWrapper;
import com.brandmaker.mediapoolmalbridge.tasks.threadpoolexecutor.model.ComparableRunnableWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * Class that collect common fields and methods for unique thread services
 */
public abstract class AbstractUniqueThreadService extends AbstractService {

    @Autowired
    protected AssetRepository assetRepository;

    @Autowired
    protected MalPriorities malPriorities;

    protected AbstractNonUniqueThreadService<AssetEntity> assetService;

    private final Lock lock = new ReentrantLock();

    protected abstract void run();

    protected abstract TaskExecutorWrapper getTaskExecutorWrapper();

    protected abstract int getTaskExecutorQueueSize();

    protected abstract int getTaskExecutorMaxQueueSize();

    public void start() {
        final TaskExecutorWrapper taskExecutorWrapper = getTaskExecutorWrapper();
        serviceRepository.save(new ServiceEntity(ServiceState.SERVICE_START, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize()));

        if (lock.tryLock()) {
            try {
                serviceRepository.save(new ServiceEntity(ServiceState.SERVICE_LOCK_ACQUIRED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize()));

                if (isRunService()) {
                    run();
                }
            } catch (final Exception e) {
                final String message = String.format("Error occured while executing service [%s], with message [%s]", getClass().getName(), e.getMessage());
                final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null);
                reportsRepository.save(reportsEntity);
                logger.error(message, e);
            } finally {
                lock.unlock();
            }
        } else {
            serviceRepository.save(new ServiceEntity(ServiceState.SERVICE_LOCK_ACQUIRE_FAILED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize()));
            return;
        }
        serviceRepository.save(new ServiceEntity(ServiceState.SERVICE_FINISHED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize()));
    }

    protected List<AssetEntity> getAssetEntitiesWithBrand(final TransferringAssetStatus fromStatus, final String brandId) {
        if ("null".equals(brandId)) {
            return assetRepository.findAllByTransferringAssetStatusAndBrandIdAndUpdatedIsAfter(
                    fromStatus, null, getMidnightBridgeLookInThePast(), PageRequest.of(0, appConfig.getDatabasePageSize()));
        } else {
            return assetRepository.findAllByTransferringAssetStatusAndBrandIdAndUpdatedIsAfter(
                    fromStatus, brandId, getMidnightBridgeLookInThePast(), PageRequest.of(0, appConfig.getDatabasePageSize()));
        }
    }

    protected void executeTransition(final TransferringAssetStatus fromStatus,
                                     final TransferringAssetStatus intermediateStatus,
                                     final Predicate<MALAssetOperation> predicate) {
        try {
            malPriorities.loadPriorities();

            final TaskExecutorWrapper taskExecutorWrapper = getTaskExecutorWrapper();
            final List<Integer> orderedPriorities = malPriorities.getOrderedPriorities();
            for (final Integer priority : orderedPriorities) {
                for (final String brandId : malPriorities.getBrandIdsForPriority(priority)) {
                    for (int page = 0; true; ++page) {
                        final List<AssetEntity> assetEntities = getAssetEntitiesWithBrand(fromStatus, brandId);

                        if (assetEntities.isEmpty() || page > getTaskExecutorMaxQueueSize() / appConfig.getDatabasePageSize()) {
                            break;
                        }
                        taskExecutorWrapper.lock();
                        setMalAssetIntermediateStatus(intermediateStatus, predicate, taskExecutorWrapper, assetEntities);
                    }
                }
            }
        } catch (final Exception e) {
            logger.error("Service {} exited with error message {}", getClass().getName(), e.getMessage(), e);
        }
    }

    private void setMalAssetIntermediateStatus(TransferringAssetStatus intermediateStatus, Predicate<MALAssetOperation> predicate, TaskExecutorWrapper taskExecutorWrapper, List<AssetEntity> assetEntities) {
        try {
            assetEntities.forEach(assetEntity -> {
                if (predicate.test(assetEntity.getMalAssetOperation()) && taskExecutorWrapper.canAcceptNewTask()) {
                    assetEntity.setTransferringAssetStatus(intermediateStatus);
                    assetRepository.save(assetEntity);
                    getTaskExecutorWrapper().getTaskExecutor()
                            .execute(new ComparableRunnableWrapper(() -> assetService.start(assetEntity), malPriorities.getMalPriority(assetEntity.getBrandId()), assetEntity.getMalAssetId()));
                }
            });
        } catch (final Exception e) {
            logger.error("Exception occurred during putting load on task executor in {}", getClass().getName(), e);
        } finally {
            getTaskExecutorWrapper().unlock();
        }
    }
}
