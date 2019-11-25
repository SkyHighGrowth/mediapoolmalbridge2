package MediaPoolMalBridge.service;

import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.schedule.ServiceState;
import MediaPoolMalBridge.persistence.entity.schedule.ServiceEntity;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractUniqueService extends AbstractService {

    private Lock lock = new ReentrantLock();

    protected abstract void run( );

    public void start( )
    {
        storeServiceEntity( new ServiceEntity( ServiceState.SERVICE_START, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount()));

        try {
            if (lock.tryLock()) {

                storeServiceEntity(new  ServiceEntity(ServiceState.SERVICE_LOCK_ACQUIRED, getClass().getCanonicalName(),Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount()) );

                if( isRunService() ) {
                    run( );
                }
            } else {
                storeServiceEntity( new ServiceEntity(ServiceState.SERVICE_LOCK_ACQUIRE_FAILED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount()));
            }
            storeServiceEntity( new ServiceEntity( ServiceState.SERVICE_FINISHED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount() ) );
        } catch ( final Exception e ) {
            final String message = String.format( "Error occured while executing service [%s], with message [%s]", getClass().getName(), e.getMessage() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error( message, e );
        } finally {
            lock.unlock();
        }
    }

    protected void storeServiceEntity( final ServiceEntity serviceEntity )
    {
        serviceRepository.save( serviceEntity );
    }
}
