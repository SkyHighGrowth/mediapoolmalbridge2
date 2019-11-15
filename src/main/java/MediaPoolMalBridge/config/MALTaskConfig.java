package MediaPoolMalBridge.config;

import MediaPoolMalBridge.tasks.MAL.TaskExecutorWrapper;
import MediaPoolMalBridge.tasks.MAL.TaskSchedulerWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class MALTaskConfig {

    private final int executorThreadPoolSize;

    private final int schedulerThreadPoolSize;

    public MALTaskConfig(@Value("${mal.executor.thread.pool.size}") final int executorThreadPoolSize,
                         @Value("${mal.scheduler.thread.pool.size}") final int schedulerThreadPoolSize) {
        this.executorThreadPoolSize = executorThreadPoolSize;
        this.schedulerThreadPoolSize = schedulerThreadPoolSize;
    }

    @Bean
    public TaskExecutorWrapper taskExecutorWrapper() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("MAL-polling-task-executor-");
        threadPoolTaskExecutor.setCorePoolSize(executorThreadPoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(executorThreadPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(1);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return new TaskExecutorWrapper(threadPoolTaskExecutor);
    }

    @Bean
    public TaskSchedulerWrapper taskSchedulerWrapper() {
        final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("MAL-polling-task-scheduler-");
        threadPoolTaskScheduler.setPoolSize(schedulerThreadPoolSize);
        return new TaskSchedulerWrapper(threadPoolTaskScheduler);
    }
}
