package MediaPoolMalBridge.service;

import MediaPoolMalBridge.persistence.entity.Bridge.schedule.ServiceEntity;
import MediaPoolMalBridge.persistence.entity.enums.schedule.ServiceState;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractNonUniqueThreadService<RUN_ARGUMENT> extends AbstractService {

    @Autowired
    protected AssetRepository assetRepository;

    protected abstract void run( final RUN_ARGUMENT run_argument );

    public void start( final RUN_ARGUMENT runArgument )
    {
        storeServiceEntity( new ServiceEntity( ServiceState.SERVICE_START, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize() ) );

        if( isRunService() ) {
            run( runArgument );
        }

        storeServiceEntity( new ServiceEntity( ServiceState.SERVICE_FINISHED, getClass().getCanonicalName(), Thread.currentThread().getName(), taskExecutorWrapper.getTaskExecutor().getActiveCount(), taskExecutorWrapper.getQueueSize() ));
    }

    @Transactional
    protected void storeServiceEntity( final ServiceEntity serviceEntity )
    {
        serviceRepository.save( serviceEntity );
    }
}
