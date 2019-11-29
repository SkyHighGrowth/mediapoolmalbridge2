package MediaPoolMalBridge.service;

import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.schedule.ServiceEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.schedule.ServiceState;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractUniqueThreadService extends AbstractService {

    @Autowired
    protected AssetRepository assetRepository;

    private Lock lock = new ReentrantLock();

    protected abstract void run( );

    public void start( )
    {
        storeServiceEntity( new ServiceEntity( ServiceState.SERVICE_START, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize()));

        try {
            if (lock.tryLock()) {

                storeServiceEntity(new  ServiceEntity(ServiceState.SERVICE_LOCK_ACQUIRED, getClass().getCanonicalName(),Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize()) );

                if( isRunService() ) {
                    run( );
                }
            } else {
                storeServiceEntity( new ServiceEntity(ServiceState.SERVICE_LOCK_ACQUIRE_FAILED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize()));
            }
            storeServiceEntity( new ServiceEntity( ServiceState.SERVICE_FINISHED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize() ) );
        } catch ( final Exception e ) {
            final String message = String.format( "Error occured while executing service [%s], with message [%s]", getClass().getName(), e.getMessage() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error( message, e );
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    protected void storeServiceEntity( final ServiceEntity serviceEntity )
    {
        serviceRepository.save( serviceEntity );
    }
}
