package MediaPoolMalBridge.service;

import MediaPoolMalBridge.persistence.entity.enums.schedule.JobState;
import MediaPoolMalBridge.persistence.entity.schedule.JobEntity;
import MediaPoolMalBridge.persistence.repository.ReportsRepository;
import MediaPoolMalBridge.persistence.repository.schedule.JobRepository;
import MediaPoolMalBridge.tasks.TaskSchedulerWrapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Arrays;

public abstract class AbstractSchedulerService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected static final Gson GSON = new Gson();

    @Autowired
    protected TaskSchedulerWrapper taskSchedulerWrapper;

    @Autowired
    protected ReportsRepository reportsRepository;

    @Autowired
    protected Environment environment;

    @Autowired
    protected JobRepository jobRepository;

    private boolean isRunScheduler() {
        return Arrays.asList(environment.getActiveProfiles()).contains("scheduler");
    }

    protected abstract void scheduled();

    protected void run()
    {
        logger.debug( "Job started" );
        storeJobEntity( new JobEntity( JobState.JOB_START, getClass().getName(), Thread.currentThread().getName(), taskSchedulerWrapper.getTaskScheduler().getActiveCount() ) );

        if( isRunScheduler() ) {
            scheduled();
        }

        storeJobEntity(new JobEntity(JobState.JOB_END, getClass().getName(), Thread.currentThread().getName(), taskSchedulerWrapper.getTaskScheduler().getActiveCount() ) );
        logger.debug("Job finished");
    }

    protected void storeJobEntity( final JobEntity jobEntity )
    {
        jobRepository.save( jobEntity );
    }
}
