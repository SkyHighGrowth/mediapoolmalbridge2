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
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
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

    protected AbstractNonUniqueThreadService<AssetEntity> assetService;

    private Lock lock = new ReentrantLock();

    protected abstract void run( );

    protected abstract TaskExecutorWrapper getTaskExecutorWrapper();

    protected abstract int getTaskExecutorQueueSize();

    public void start( )
    {
        final TaskExecutorWrapper taskExecutorWrapper = getTaskExecutorWrapper();
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

    protected List<AssetEntity> getAssetEntities( final TransferringAssetStatus fromStatus ) {
        return assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(
                fromStatus, getMidnightBridgeLookInThePast(), PageRequest.of(0, appConfig.getDatabasePageSize()));
    }

    protected void executeTransition(final TransferringAssetStatus fromStatus,
                                     final TransferringAssetStatus intermediateStatus,
                                     final TransferringAssetStatus toStatus,
                                     final Predicate<MALAssetOperation> predicate)
    {
        final TaskExecutorWrapper taskExecutorWrapper = getTaskExecutorWrapper();
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final List<AssetEntity> assetEntities = getAssetEntities( fromStatus );

            if( assetEntities.isEmpty() || page > getTaskExecutorQueueSize()/appConfig.getDatabasePageSize() ) {
                break;
            }
            taskExecutorWrapper.lock();
            try {
                assetEntities.forEach(assetEntity -> {
                    if( predicate.test( assetEntity.getMalAssetOperation() ) ) {
                        if ( taskExecutorWrapper.canAcceptNewTask() ) {
                            assetEntity.setTransferringAssetStatus(intermediateStatus);
                            assetRepository.save( assetEntity );
                            getTaskExecutorWrapper().getTaskExecutor().execute(() -> assetService.start(assetEntity));
                        }
                    }
                });
            } catch ( final Exception e ) {
                logger.error( "Exception occurred during putting load on task executor in {}", getClass().getName(), e );
            } finally {
                getTaskExecutorWrapper().unlock();
            }
        }
    }
}
