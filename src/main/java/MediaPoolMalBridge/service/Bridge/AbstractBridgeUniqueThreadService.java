package MediaPoolMalBridge.service.Bridge;

import MediaPoolMalBridge.service.AbstractUniqueThreadService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractBridgeUniqueThreadService extends AbstractUniqueThreadService  {

    @Autowired
    @Qualifier( "MALTaskExecutorWrapper" )
    protected TaskExecutorWrapper taskExecutorWrapper;

    @Override
    protected TaskExecutorWrapper getTaskExecutorWrapper() {
        return taskExecutorWrapper;
    }

    @Override
    protected int getTaskExecutorQueueSize() {
        return taskExecutorWrapper.getQueueSize();
    }

    @Override
    protected int getTaskExecutorMaxQueueSize() {
        return taskExecutorWrapper.getMaximalQueueSize();
    }
}
