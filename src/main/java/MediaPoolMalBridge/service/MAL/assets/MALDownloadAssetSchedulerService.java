package MediaPoolMalBridge.service.MAL.assets;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.assets.download.MALFireDownloadAssetsService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MALDownloadAssetSchedulerService extends AbstractSchedulerService {

    private final MALFireDownloadAssetsService malFireDownloadAssetsService;

    public MALDownloadAssetSchedulerService(final MALFireDownloadAssetsService malFireDownloadAssetsService) {
        this.malFireDownloadAssetsService = malFireDownloadAssetsService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
    }

    @Override
    public void scheduled() {
        malFireDownloadAssetsService.start();
    }
}

