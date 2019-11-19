package MediaPoolMalBridge.service;

import MediaPoolMalBridge.persistence.repository.ReportsRepository;
import MediaPoolMalBridge.tasks.TaskSchedulerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Arrays;

public abstract class AbstractSchedulerService extends AbstractService {

    @Autowired
    protected TaskSchedulerWrapper taskSchedulerWrapper;

    @Autowired
    protected ReportsRepository reportsRepository;

    @Autowired
    private Environment environment;

    public boolean isDev() {
        return Arrays.asList(environment.getActiveProfiles()).contains("dev");
    }

    public boolean isRunScheduler() {
        return Arrays.asList(environment.getActiveProfiles()).contains("scheduler");
    }
}
