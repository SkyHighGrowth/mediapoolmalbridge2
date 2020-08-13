package MediaPoolMalBridge.service.MAL.assets;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.assets.download.MALFireDownloadAssetsUniqueThreadService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service which triggers {@link MALFireDownloadAssetsUniqueThreadService}
 */
@Service
@DependsOn( "BridgeDatabaseNormalizerService" )
public class MALDownloadAssetSchedulerService extends AbstractSchedulerService {

    private final MALFireDownloadAssetsUniqueThreadService malFireDownloadAssetsUniqueThreadService;

    public MALDownloadAssetSchedulerService(final MALFireDownloadAssetsUniqueThreadService malFireDownloadAssetsUniqueThreadService) {
        this.malFireDownloadAssetsUniqueThreadService = malFireDownloadAssetsUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getMalDownloadAssetCronExpression()));
    }

    @Override
    public void scheduled() {
        logger.info("Downloading assets from MAL started");
        malFireDownloadAssetsUniqueThreadService.start();
        logger.info("Downloading assets from MAL ended");
    }
}

