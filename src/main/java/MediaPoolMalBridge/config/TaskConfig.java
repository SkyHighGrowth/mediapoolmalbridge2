package MediaPoolMalBridge.config;

import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import MediaPoolMalBridge.tasks.TaskSchedulerWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TaskConfig {

    private final int executorThreadPoolSize;

    private final int executorThreadQueueSize;

    private final int schedulerThreadPoolSize;

    public TaskConfig(@Value("${threadexecutor.pool.size}") final int executorThreadPoolSize,
                      @Value("${threadexecutor.queue.size}") final int executorThreadQueueSize,
                      @Value("${threadscheduler.pool.size}") final int schedulerThreadPoolSize) {
        this.executorThreadPoolSize = executorThreadPoolSize;
        this.schedulerThreadPoolSize = schedulerThreadPoolSize;
        this.executorThreadQueueSize = executorThreadQueueSize;
    }

    @Bean
    public TaskExecutorWrapper taskExecutorWrapper() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("task-executor-");
        threadPoolTaskExecutor.setCorePoolSize(executorThreadPoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(executorThreadPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(executorThreadQueueSize);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return new TaskExecutorWrapper(threadPoolTaskExecutor);
    }

    @Bean
    public TaskSchedulerWrapper taskSchedulerWrapper() {
        final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("task-scheduler-");
        threadPoolTaskScheduler.setPoolSize(schedulerThreadPoolSize);
        return new TaskSchedulerWrapper(threadPoolTaskScheduler);
    }
}
