package MediaPoolMalBridge.service;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.schedule.ServiceEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.schedule.ServiceState;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class that collect common fields and methods for unique thread services
 */
public abstract class AbstractUniqueThreadService extends AbstractService {

    @Autowired
    protected AssetRepository assetRepository;

    protected AbstractNonUniqueThreadService<AssetEntity> assetService;

    private Lock lock = new ReentrantLock();

    protected abstract void run( );

    public void start( )
    {
        serviceRepository.save( new ServiceEntity( ServiceState.SERVICE_START, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize() ) );

        if (lock.tryLock()) {
            try {
                serviceRepository.save(new  ServiceEntity(ServiceState.SERVICE_LOCK_ACQUIRED, getClass().getCanonicalName(),Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize()) );

                if( isRunService() ) {
                    run( );
                }
            } catch ( final Exception e ) {
                final String message = String.format("Error occured while executing service [%s], with message [%s]", getClass().getName(), e.getMessage());
                final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null);
                reportsRepository.save(reportsEntity);
                logger.error(message, e);
            } finally {
                lock.unlock();
            }
        } else {
            serviceRepository.save( new ServiceEntity(ServiceState.SERVICE_LOCK_ACQUIRE_FAILED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize()));
            return;
        }
        serviceRepository.save( new ServiceEntity( ServiceState.SERVICE_FINISHED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize() ) );
    }

    protected void executeTransition(final TransferringAssetStatus fromStatus,
                                     final TransferringAssetStatus intermediateStatus,
                                     final TransferringAssetStatus toStatus,
                                     final MALAssetOperation malAssetOperation)
    {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(
                        fromStatus, getMidnight(), PageRequest.of(page, appConfig.getDatabasePageSize()));

            condition = assetEntities.hasNext();
            synchronized (taskExecutorWrapper) {
                assetEntities.forEach(assetEntity -> {
                    if( malAssetOperation == null || malAssetOperation.equals(assetEntity.getMalAssetOperation() ) ) {
                        if (taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax()) {
                            assetEntity.setTransferringAssetStatus(intermediateStatus);
                            assetRepository.save( assetEntity );
                            taskExecutorWrapper.getTaskExecutor().execute(() -> assetService.start(assetEntity));
                        }
                    }
                });
            }

        }
    }
}
