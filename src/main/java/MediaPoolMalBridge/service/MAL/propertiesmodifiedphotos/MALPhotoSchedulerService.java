package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.download.MALFireDownloadPhotoService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MALPhotoSchedulerService extends AbstractSchedulerService {

    private final MALFireDownloadPhotoService malFireDownloadPhotoService;

    public MALPhotoSchedulerService(final MALFireDownloadPhotoService malFireDownloadPhotoService) {
        this.malFireDownloadPhotoService = malFireDownloadPhotoService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
    }

    public void scheduled() {
        malFireDownloadPhotoService.start();
    }
}
