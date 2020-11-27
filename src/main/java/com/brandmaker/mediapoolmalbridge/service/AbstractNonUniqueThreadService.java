package com.brandmaker.mediapoolmalbridge.service;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.schedule.ServiceEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.schedule.ServiceState;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.AssetRepository;
import com.brandmaker.mediapoolmalbridge.tasks.TaskExecutorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class that collects common fields and properties for non unique thread services
 * @param <RUN_ARGUMENT>
 */
public abstract class AbstractNonUniqueThreadService<RUN_ARGUMENT> extends AbstractService {

    @Autowired
    protected AssetRepository assetRepository;

    protected abstract void run( final RUN_ARGUMENT run_argument );

    protected abstract TaskExecutorWrapper getTaskExecutorWrapper();

    public void start( final RUN_ARGUMENT runArgument )
    {
        final TaskExecutorWrapper taskExecutorWrapper = getTaskExecutorWrapper();
        storeServiceEntity( new ServiceEntity( ServiceState.SERVICE_START, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize() ) );

        if( isRunService() ) {
            try {
                run(runArgument);
            } catch( final Exception e ) {
                final String message = String.format( "Service [%s] exited with an exception, with message [%s]", getClass().getName(), e.getMessage() );
                logger.error( message, e );
                final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null );
                reportsRepository.save( reportsEntity );
            }
        }

        storeServiceEntity( new ServiceEntity( ServiceState.SERVICE_FINISHED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize() ));
    }

    @Transactional
    public void storeServiceEntity( final ServiceEntity serviceEntity )
    {
        serviceRepository.save( serviceEntity );
    }
}
