package MediaPoolMalBridge.service;

import MediaPoolMalBridge.tasks.MAL.TaskSchedulerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Arrays;

public abstract class AbstractSchedulerService extends AbstractService {

    @Autowired
    protected TaskSchedulerWrapper taskSchedulerWrapper;

    @Autowired
    private Environment environment;

    public boolean isDev()
    {
        return Arrays.asList( environment.getActiveProfiles() ).contains( "dev" );
    }

    public boolean isRunScheduler() { return Arrays.asList( environment.getActiveProfiles()).contains( "scheduler" ); }
}
