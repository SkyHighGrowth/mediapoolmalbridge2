package MediaPoolMalBridge.service;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.persistence.repository.Bridge.ReportsRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.schedule.ServiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

/**
 * Class that collects common fields and methods for services
 */
public abstract class AbstractService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier( "GsonSerializer" )
    protected Gson GSON;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AppConfig appConfig;

    @Autowired
    protected Environment environment;

    protected boolean isRunService() {
        return !Arrays.asList(environment.getActiveProfiles()).contains("no service");
    }

    @Autowired
    protected ServiceRepository serviceRepository;

    @Autowired
    protected ReportsRepository reportsRepository;

    protected LocalDateTime getMidnightBridgeLookInThePast() {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .minusDays(appConfig.getBridgeLookInThePastDays())
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
    }
}
