package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.download.MALFireDownloadPhotoService;
import MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.modified.MALCollectModifiedPropertiesPhotosService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class MALPhotoSchedulerService extends AbstractSchedulerService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final MALFireDownloadPhotoService malFireDownloadPhotoService;

    private final MALCollectModifiedPropertiesPhotosService malCollectModifiedPropertiesPhotosService;

    public MALPhotoSchedulerService(final MALFireDownloadPhotoService malFireDownloadPhotoService,
                                    final MALCollectModifiedPropertiesPhotosService malCollectModifiedPropertiesPhotosService) {
        this.malFireDownloadPhotoService = malFireDownloadPhotoService;
        this.malCollectModifiedPropertiesPhotosService = malCollectModifiedPropertiesPhotosService;
    }

    @PostConstruct
    public void scheduleUpdates() {
        if (isRunScheduler()) {
            taskSchedulerWrapper.getTaskScheduler().schedule(this::update, new CronTrigger(Constants.CRON_DAILY_TRIGGGER_EXPRESSION));
            taskSchedulerWrapper.getTaskScheduler().schedule(this::downloadPropertyPhotos, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
        }
    }

    public void update() {
        final String since = Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .format(DATE_TIME_FORMATTER);
        logger.info("Executing property phot update task, modified since {} UTC", since);
        malCollectModifiedPropertiesPhotosService.downloadModifiedPhotos(since);
    }

    public void downloadPropertyPhotos() {
        malFireDownloadPhotoService.downloadPhotos();
    }
}
