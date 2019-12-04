package MediaPoolMalBridge.config;

import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import MediaPoolMalBridge.tasks.TaskSchedulerWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Scheduler and Executor beans
 */
@Configuration
public class TaskConfig {

    private AppConfig appConfig;

    public TaskConfig(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Bean
    public TaskExecutorWrapper taskExecutorWrapper() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("task-executor-");
        threadPoolTaskExecutor.setCorePoolSize(appConfig.getThreadexecutorPoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(appConfig.getThreadexecutorPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(appConfig.getThreadexecutorQueueSize());
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return new TaskExecutorWrapper(threadPoolTaskExecutor);
    }

    @Bean
    public TaskSchedulerWrapper taskSchedulerWrapper() {
        final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("task-scheduler-");
        threadPoolTaskScheduler.setPoolSize(appConfig.getThreadschedulerPoolSize());
        return new TaskSchedulerWrapper(threadPoolTaskScheduler);
    }
}
