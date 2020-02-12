package MediaPoolMalBridge.tasks;

import MediaPoolMalBridge.tasks.threadpoolexecutor.ThreadPoolPriorityTaskExecutor;
import org.springframework.beans.factory.DisposableBean;

public class TaskPriorityExecutorWrapper extends TaskExecutorWrapper implements DisposableBean {

    public TaskPriorityExecutorWrapper(final ThreadPoolPriorityTaskExecutor taskExecutor) {
        super( taskExecutor );
    }
}
