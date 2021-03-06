package com.brandmaker.mediapoolmalbridge.config;

import com.brandmaker.mediapoolmalbridge.tasks.TaskExecutorWrapper;
import com.brandmaker.mediapoolmalbridge.tasks.TaskPriorityExecutorWrapper;
import com.brandmaker.mediapoolmalbridge.tasks.TaskSchedulerWrapper;
import com.brandmaker.mediapoolmalbridge.tasks.threadpoolexecutor.ThreadPoolPriorityTaskExecutor;
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

    private final AppConfig appConfig;

    public TaskConfig(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Bean( "MALTaskExecutorWrapper" )
    public TaskExecutorWrapper malTaskExecutorWrapper() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("mal-task-executor-");
        AppConfigData appConfigData = appConfig.getAppConfigData();
        threadPoolTaskExecutor.setCorePoolSize(appConfigData.getMalThreadexecutorPoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(appConfigData.getMalThreadexecutorPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(appConfigData.getMalThreadexecutorQueueSize());
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        final TaskExecutorWrapper taskExecutorWrapper = new TaskExecutorWrapper(threadPoolTaskExecutor);
        taskExecutorWrapper.setMaximalQueueSize( appConfigData.getMalThreadexecutorQueueLengthMax() );
        return taskExecutorWrapper;
    }

    @Bean( "BMTaskExecutorWrapper" )
    public TaskExecutorWrapper bmTaskExecutorWrapper() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("bm-task-executor-");
        AppConfigData appConfigData = appConfig.getAppConfigData();
        threadPoolTaskExecutor.setCorePoolSize(appConfigData.getBmThreadexecutorPoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(appConfigData.getBmThreadexecutorPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(appConfigData.getBmThreadexecutorQueueSize());
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        final TaskExecutorWrapper taskExecutorWrapper = new TaskExecutorWrapper(threadPoolTaskExecutor);
        taskExecutorWrapper.setMaximalQueueSize( appConfigData.getBmThreadexecutorQueueLengthMax() );
        return taskExecutorWrapper;
    }

    @Bean( "BMTaskPriorityExecutorWrapper" )
    public TaskPriorityExecutorWrapper bmTaskPriorityExecutorWrapper() {
        final ThreadPoolPriorityTaskExecutor threadPoolTaskExecutor = new ThreadPoolPriorityTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("bm-task-priority-executor-");
        AppConfigData appConfigData = appConfig.getAppConfigData();
        threadPoolTaskExecutor.setCorePoolSize(appConfigData.getBmThreadexecutorPoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(appConfigData.getBmThreadexecutorPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(appConfigData.getBmThreadexecutorQueueSize());
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        final TaskPriorityExecutorWrapper taskExecutorWrapper = new TaskPriorityExecutorWrapper(threadPoolTaskExecutor);
        taskExecutorWrapper.setMaximalQueueSize( appConfigData.getBmThreadexecutorQueueLengthMax() );
        return taskExecutorWrapper;
    }

    @Bean
    public TaskSchedulerWrapper taskSchedulerWrapper() {
        final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("task-scheduler-");
        threadPoolTaskScheduler.setPoolSize(appConfig.getAppConfigData().getThreadschedulerPoolSize());
        return new TaskSchedulerWrapper(threadPoolTaskScheduler);
    }
}
