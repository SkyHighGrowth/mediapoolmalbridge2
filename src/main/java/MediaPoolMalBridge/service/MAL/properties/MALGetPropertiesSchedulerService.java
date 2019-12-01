package MediaPoolMalBridge.service.MAL.properties;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.properties.deleted.MALGetPropertiesDeletedUniqueThreadSinceService;
import MediaPoolMalBridge.service.MAL.properties.download.MALGetPropertiesUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class MALGetPropertiesSchedulerService extends AbstractSchedulerService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm:ss");

    private MALGetPropertiesUniqueThreadService getPropertiesService;

    private MALGetPropertiesDeletedUniqueThreadSinceService getPropertiesDeletedSinceService;

    public MALGetPropertiesSchedulerService(final MALGetPropertiesUniqueThreadService getPropertiesService,
                                            final MALGetPropertiesDeletedUniqueThreadSinceService getPropertiesDeletedSinceService) {
        this.getPropertiesService = getPropertiesService;
        this.getPropertiesDeletedSinceService = getPropertiesDeletedSinceService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getMalPropertiesCronExpression()));
    }

    @Override
    public void scheduled() {
        final String since = Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .minusDays(300)
                .format(DATE_TIME_FORMATTER);
        getPropertiesService.start();
        getPropertiesDeletedSinceService.setUnavailableSince( since );
        getPropertiesDeletedSinceService.start();
    }
}
