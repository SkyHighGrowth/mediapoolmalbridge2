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
    public void schedulePropertiesUpdate() {
        if (isRunScheduler()) {
            taskSchedulerWrapper.getTaskScheduler()
                    .schedule(this::update, new CronTrigger(Constants.CRON_DAILY_TRIGGGER_EXPRESSION));
        }
    }

    public void update() {
        final String since = Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .format(DATE_TIME_FORMATTER);
        getPropertiesService.downloadProperties();
        getPropertiesDeletedService.download(since);
    }
}
