package MediaPoolMalBridge.service.Bridge.ktistotheme;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.theme.download.BMDownloadThemeService;
import MediaPoolMalBridge.service.BrandMaker.theme.upload.BMFireUploadThemeService;
import MediaPoolMalBridge.service.MAL.kits.MALGetKitsService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MALKitsToBMThemeSchedulerService extends AbstractSchedulerService {

    private MALGetKitsService malGetKitsService;

    private BMDownloadThemeService bmDownloadThemeService;

    private BMFireUploadThemeService bmFireUploadThemeService;

    private MALKitsToBMThemeSchedulerService(final MALGetKitsService malGetKitsService,
                                             final BMDownloadThemeService bmDownloadThemeService,
                                             final BMFireUploadThemeService bmFireUploadThemeService) {
        this.malGetKitsService = malGetKitsService;
        this.bmDownloadThemeService = bmDownloadThemeService;
        this.bmFireUploadThemeService = bmFireUploadThemeService;
    }

    @PostConstruct
    public void exchangeKitsToThemes() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
    }

    public void scheduled() {
        malGetKitsService.start();
        bmDownloadThemeService.start();
        bmFireUploadThemeService.start();
    }
}
