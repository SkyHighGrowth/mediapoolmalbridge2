package MediaPoolMalBridge.service.BrandMaker.theme.themeid;

import MediaPoolMalBridge.clients.BrandMaker.model.BMTheme;
import MediaPoolMalBridge.clients.BrandMaker.themeid.client.BMDownloadThemeIdClient;
import MediaPoolMalBridge.clients.BrandMaker.themeid.client.model.DownloadThemeIdResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.theme.themeid.model.BMThemePathToId;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

/**
 * Scheduler service that downloads theme ids for given theme path from Mediapool server
 */
@Service
public class BMGetThemeIdSchedulerService extends AbstractSchedulerService {

    private BMDownloadThemeIdClient bmDownloadThemeIdClient;

    private BMThemePathToId bmThemePathToId;

    public BMGetThemeIdSchedulerService(final BMDownloadThemeIdClient bmDownloadThemeIdClient,
                                        final BMThemePathToId bmThemePathToId)
    {
        this.bmDownloadThemeIdClient = bmDownloadThemeIdClient;
        this.bmThemePathToId = bmThemePathToId;
    }

    //@PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule( this::scheduled, new CronTrigger( appConfig.getBmGetThemeIdCronExpression() ) );
    }

    @Override
    protected void scheduled() {
        for( final String themePath : bmThemePathToId.keySet() ) {
            final BMTheme theme = new BMTheme();
            theme.setThemePath( themePath );
            final DownloadThemeIdResponse response = bmDownloadThemeIdClient.downloadThemeId( theme );
            if( response.isStatus() ) {
                bmThemePathToId.put( themePath, String.valueOf( response.getTheme().getId() ) );
            } else {
                final String message = String.format( "Can not download theme id from theme path [%s], with messages [%s] and warnings [%s]", themePath, response.getErrorAsString(), response.getWarningsAsString() );
                final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null );
                reportsRepository.save( reportsEntity );
                logger.error( message );
            }
        }
    }
}
