package MediaPoolMalBridge.service.MAL.properties;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.properties.deleted.MALGetPropertiesDeletedService;
import MediaPoolMalBridge.service.MAL.properties.download.MALGetPropertiesService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class MALGetPropertiesSchedulerService extends AbstractSchedulerService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private MALGetPropertiesService getPropertiesService;

    private MALGetPropertiesDeletedService getPropertiesDeletedService;

    public MALGetPropertiesSchedulerService(final MALGetPropertiesService getPropertiesService,
                                            final MALGetPropertiesDeletedService getPropertiesDeletedService) {
        this.getPropertiesService = getPropertiesService;
        this.getPropertiesDeletedService = getPropertiesDeletedService;
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
        getPropertiesService.start();
        getPropertiesDeletedService.setUnavailableSince( since );
        getPropertiesDeletedService.start();
    }
}
