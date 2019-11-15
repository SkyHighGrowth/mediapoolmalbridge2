package MediaPoolMalBridge.service.Bridge.ktistotheme;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.theme.download.BMDownloadThemeService;
import MediaPoolMalBridge.service.BrandMaker.theme.upload.BMUploadThemeService;
import MediaPoolMalBridge.service.MAL.kits.MALGetKitsService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MALKitsToBMThemeSchedulerService extends AbstractSchedulerService {

    private MALGetKitsService malGetKitsService;

    private BMDownloadThemeService bmDownloadThemeService;

    private BMUploadThemeService bmUploadThemeService;

    private MALKitsToBMThemeSchedulerService( final MALGetKitsService malGetKitsService,
                                              final BMDownloadThemeService bmDownloadThemeService,
                                              final BMUploadThemeService bmUploadThemeService)
    {
        this.malGetKitsService = malGetKitsService;
        this.bmDownloadThemeService = bmDownloadThemeService;
        this.bmUploadThemeService = bmUploadThemeService;
    }

    @PostConstruct
    public void exchangeKitsToThemes()
    {
        if( isRunScheduler() )
        {
            taskSchedulerWrapper.getTaskScheduler().schedule( this::exchange, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION ) );
        }
    }

    public void exchange()
    {
        malGetKitsService.downloadKits();
        bmDownloadThemeService.download();
        bmUploadThemeService.uploadTheme();
    }
}
