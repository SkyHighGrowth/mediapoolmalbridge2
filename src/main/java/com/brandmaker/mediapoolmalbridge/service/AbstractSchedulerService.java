package com.brandmaker.mediapoolmalbridge.service;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.schedule.JobEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.schedule.JobState;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.ReportsRepository;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.schedule.JobRepository;
import com.brandmaker.mediapoolmalbridge.tasks.TaskSchedulerWrapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Class that collects common fields and methods for scheduler services
 */
public abstract class AbstractSchedulerService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected static final Gson GSON = new Gson();

    private final Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    @Autowired
    protected AppConfig appConfig;

    @Autowired
    protected TaskSchedulerWrapper taskSchedulerWrapper;

    @Autowired
    protected ReportsRepository reportsRepository;

    @Autowired
    protected Environment environment;

    @Autowired
    protected JobRepository jobRepository;

    private boolean isRunScheduler() {
        return !Arrays.asList(environment.getActiveProfiles()).contains("no scheduler");
    }

    protected abstract void scheduled();

    protected void run()
    {
        logger.debug( "Job started" );
        storeJobEntity( new JobEntity( JobState.JOB_START, getClass().getName(), Thread.currentThread().getName(), taskSchedulerWrapper.getTaskScheduler().getActiveCount(), taskSchedulerWrapper.getTaskScheduler().getScheduledThreadPoolExecutor().getQueue().size() ) );

        if( isRunScheduler() ) {
            scheduled();
        }

        storeJobEntity(new JobEntity(JobState.JOB_END, getClass().getName(), Thread.currentThread().getName(), taskSchedulerWrapper.getTaskScheduler().getActiveCount(), taskSchedulerWrapper.getTaskScheduler().getScheduledThreadPoolExecutor().getQueue().size() ) );
        logger.debug("Job finished");
    }

    public void jobSchedule(CronTrigger trigger) {
        ScheduledFuture<?> currentJob = jobsMap.get(this.getClass().getName());

        if (currentJob != null) {
            currentJob.cancel(true);
        }

        ScheduledFuture<?> schedule = taskSchedulerWrapper.getTaskScheduler().schedule(this::run, trigger);

        jobsMap.put(this.getClass().getName(), schedule);
    }

    @Transactional
    public void storeJobEntity( final JobEntity jobEntity )
    {
        jobRepository.save( jobEntity );
    }

    protected LocalDateTime getTodayMidnight() {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
    }
}
