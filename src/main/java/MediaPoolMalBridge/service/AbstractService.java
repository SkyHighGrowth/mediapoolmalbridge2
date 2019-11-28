package MediaPoolMalBridge.service;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.persistence.repository.Bridge.ReportsRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.schedule.ServiceRepository;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

public class AbstractService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected static final Gson GSON = new Gson();

    @Autowired
    protected AppConfig appConfig;

    @Autowired
    protected Environment environment;

    @Autowired
    protected TaskExecutorWrapper taskExecutorWrapper;

    protected boolean isRunService() {
        return !Arrays.asList(environment.getActiveProfiles()).contains("no service");
    }

    @Autowired
    protected ServiceRepository serviceRepository;

    @Autowired
    protected ReportsRepository reportsRepository;

    protected LocalDateTime getTodayMidnight() {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
    }

}
