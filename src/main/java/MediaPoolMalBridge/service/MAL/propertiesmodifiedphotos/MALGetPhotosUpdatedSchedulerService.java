package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.modified.MALCollectModifiedPropertiesPhotosUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class MALGetPhotosUpdatedSchedulerService extends AbstractSchedulerService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm:ss");

    private final MALCollectModifiedPropertiesPhotosUniqueThreadService malCollectModifiedPropertiesPhotosUniqueThreadService;

    public MALGetPhotosUpdatedSchedulerService(final MALCollectModifiedPropertiesPhotosUniqueThreadService malCollectModifiedPropertiesPhotosUniqueThreadService) {
        this.malCollectModifiedPropertiesPhotosUniqueThreadService = malCollectModifiedPropertiesPhotosUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getMalGetPhotosCronExpression()));
    }

    @Override
    public void scheduled() {
        final String since = Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .minusDays( appConfig.getMalLookInThePastDays() )
                .format(DATE_TIME_FORMATTER);
        logger.info("Executing property photo update task, modified since {} UTC", since);
        malCollectModifiedPropertiesPhotosUniqueThreadService.setModifiedSince( since );
        malCollectModifiedPropertiesPhotosUniqueThreadService.start();
    }
}
