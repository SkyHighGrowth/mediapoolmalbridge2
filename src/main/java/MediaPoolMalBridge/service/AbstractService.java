package MediaPoolMalBridge.service;

import MediaPoolMalBridge.persistence.repository.ReportsRepository;
import MediaPoolMalBridge.persistence.repository.schedule.ServiceRepository;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Arrays;

public class AbstractService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected static final Gson GSON = new Gson();

    @Autowired
    protected Environment environment;

    @Autowired
    protected TaskExecutorWrapper taskExecutorWrapper;

    protected boolean isRunService() {
        return Arrays.asList(environment.getActiveProfiles()).contains("service");
    }

    @Autowired
    protected ServiceRepository serviceRepository;

    @Autowired
    protected ReportsRepository reportsRepository;

}
