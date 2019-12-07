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

    @Bean( "MALTaskExecutorWrapper" )
    public TaskExecutorWrapper malTaskExecutorWrapper() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("mal-task-executor-");
        threadPoolTaskExecutor.setCorePoolSize(appConfig.getMalThreadexecutorPoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(appConfig.getMalThreadexecutorPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(appConfig.getMalThreadexecutorQueueSize());
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        final TaskExecutorWrapper taskExecutorWrapper = new TaskExecutorWrapper(threadPoolTaskExecutor);
        taskExecutorWrapper.setMaximalQueueSize( appConfig.getMalThreadexecutorQueueLengthMax() );
        return taskExecutorWrapper;
    }

    @Bean( "BMTaskExecutorWrapper" )
    public TaskExecutorWrapper bmTaskExecutorWrapper() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("bm-task-executor-");
        threadPoolTaskExecutor.setCorePoolSize(appConfig.getBmThreadexecutorPoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(appConfig.getBmThreadexecutorPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(appConfig.getBmThreadexecutorQueueSize());
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        final TaskExecutorWrapper taskExecutorWrapper = new TaskExecutorWrapper(threadPoolTaskExecutor);
        taskExecutorWrapper.setMaximalQueueSize( appConfig.getBmThreadexecutorQueueLengthMax() );
        return taskExecutorWrapper;
    }

    @Bean
    public TaskSchedulerWrapper taskSchedulerWrapper() {
        final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("task-scheduler-");
        threadPoolTaskScheduler.setPoolSize(appConfig.getThreadschedulerPoolSize());
        return new TaskSchedulerWrapper(threadPoolTaskScheduler);
    }
}
