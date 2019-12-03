package MediaPoolMalBridge.service.Bridge.ktistotheme;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.theme.download.BMDownloadThemeUniqueThreadService;
import MediaPoolMalBridge.service.BrandMaker.theme.upload.BMFireUploadThemeUniqueThreadService;
import MediaPoolMalBridge.service.MAL.kits.MALGetKitsUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Service which downloads Kits from MAL server
 */
@Service
public class BridgeTransferThemeSchedulerService extends AbstractSchedulerService {

    private MALGetKitsUniqueThreadService malGetKitsUniqueThreadService;

    private BMDownloadThemeUniqueThreadService bmDownloadThemeUniqueThreadService;

    private BMFireUploadThemeUniqueThreadService bmFireUploadThemeUniqueThreadService;

    private BridgeTransferThemeSchedulerService(final MALGetKitsUniqueThreadService malGetKitsUniqueThreadService,
                                                final BMDownloadThemeUniqueThreadService bmDownloadThemeUniqueThreadService,
                                                final BMFireUploadThemeUniqueThreadService bmFireUploadThemeUniqueThreadService) {
        this.malGetKitsUniqueThreadService = malGetKitsUniqueThreadService;
        this.bmDownloadThemeUniqueThreadService = bmDownloadThemeUniqueThreadService;
        this.bmFireUploadThemeUniqueThreadService = bmFireUploadThemeUniqueThreadService;
    }

    @PostConstruct
    public void exchangeKitsToThemes() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getBridgeTransferThemeCronExpression()));
    }

    public void scheduled() {
        malGetKitsUniqueThreadService.start();
        bmDownloadThemeUniqueThreadService.start();
        bmFireUploadThemeUniqueThreadService.start();
    }
}
