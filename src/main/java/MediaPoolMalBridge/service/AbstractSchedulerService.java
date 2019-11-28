package MediaPoolMalBridge.service;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.persistence.entity.enums.schedule.JobState;
import MediaPoolMalBridge.persistence.entity.Bridge.schedule.JobEntity;
import MediaPoolMalBridge.persistence.repository.Bridge.ReportsRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.schedule.JobRepository;
import MediaPoolMalBridge.tasks.TaskSchedulerWrapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

public abstract class AbstractSchedulerService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected static final Gson GSON = new Gson();

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

    @Transactional
    protected void storeJobEntity( final JobEntity jobEntity )
    {
        jobRepository.save( jobEntity );
    }

    protected LocalDateTime getTodayMidnight() {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .minusDays( appConfig.getBridgeLookInThePastDays() )
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
    }
}
