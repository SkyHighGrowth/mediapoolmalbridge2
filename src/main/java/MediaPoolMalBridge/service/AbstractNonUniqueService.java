package MediaPoolMalBridge.service;

import MediaPoolMalBridge.persistence.entity.enums.schedule.ServiceState;
import MediaPoolMalBridge.persistence.entity.schedule.ServiceEntity;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractNonUniqueService<RUN_ARGUMENT> extends AbstractService {

    protected abstract void run( final RUN_ARGUMENT run_argument );

    public void start( final RUN_ARGUMENT runArgument )
    {
        storeServiceEntity( new ServiceEntity( ServiceState.SERVICE_START, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount() ) );

        if( isRunService() ) {
            run( runArgument );
        }

        storeServiceEntity( new ServiceEntity( ServiceState.SERVICE_FINISHED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount() ));
    }

    protected void storeServiceEntity( final ServiceEntity serviceEntity )
    {
        serviceRepository.save( serviceEntity );
    }
}
