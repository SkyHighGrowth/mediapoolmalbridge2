package MediaPoolMalBridge.tasks.MAL;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;

public class TaskSchedulerWrapper implements DisposableBean {

    private final ThreadPoolTaskScheduler taskScheduler;

    public TaskSchedulerWrapper(final ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void initialize() {
        taskScheduler.initialize();
    }

    public ThreadPoolTaskScheduler getTaskScheduler()
    {
        return taskScheduler;
    }

    public void shutdown() {
        taskScheduler.getScheduledThreadPoolExecutor().shutdown();
    }

    @Override
    public void destroy() throws Exception {
        taskScheduler.destroy();
    }
}
