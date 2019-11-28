package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.download.MALFireDownloadPhotoUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MALPhotoSchedulerService extends AbstractSchedulerService {

    private final MALFireDownloadPhotoUniqueThreadService malFireDownloadPhotoUniqueThreadService;

    public MALPhotoSchedulerService(final MALFireDownloadPhotoUniqueThreadService malFireDownloadPhotoUniqueThreadService) {
        this.malFireDownloadPhotoUniqueThreadService = malFireDownloadPhotoUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getMalPhotoCronExpression()));
    }

    public void scheduled() {
        malFireDownloadPhotoUniqueThreadService.start();
    }
}
