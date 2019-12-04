package MediaPoolMalBridge.tasks;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;

/**
 * Wrapper class for ThreadPoolTaskExecutor
 */
public class TaskExecutorWrapper implements DisposableBean {

    private final ThreadPoolTaskExecutor taskExecutor;

    public TaskExecutorWrapper(final ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public int getQueueSize()
    {
        return taskExecutor.getThreadPoolExecutor().getQueue().size();
    }

    @PostConstruct
    public void initialize() {
        taskExecutor.initialize();
    }

    public void shutdown() {
        taskExecutor.shutdown();
    }

    @Override
    public void destroy() throws Exception {
        taskExecutor.destroy();
    }
}
