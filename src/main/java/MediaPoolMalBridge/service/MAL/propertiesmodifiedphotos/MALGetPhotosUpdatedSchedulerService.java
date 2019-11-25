package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.modified.MALCollectModifiedPropertiesPhotosService;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class MALGetPhotosUpdatedSchedulerService extends AbstractSchedulerService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final MALCollectModifiedPropertiesPhotosService malCollectModifiedPropertiesPhotosService;

    public MALGetPhotosUpdatedSchedulerService(final MALCollectModifiedPropertiesPhotosService malCollectModifiedPropertiesPhotosService) {
        this.malCollectModifiedPropertiesPhotosService = malCollectModifiedPropertiesPhotosService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(Constants.CRON_MIDNIGHT_TRIGGGER_EXPRESSION));
    }

    @Override
    public void scheduled() {
        final String since = Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .format(DATE_TIME_FORMATTER);
        logger.info("Executing property photo update task, modified since {} UTC", since);
        malCollectModifiedPropertiesPhotosService.setModifiedSince( since );
        malCollectModifiedPropertiesPhotosService.start();
    }
}
