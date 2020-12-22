package com.brandmaker.mediapoolmalbridge.service.brandmaker.theme.themeid;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.BMTheme;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.themeid.client.BMDownloadThemeIdClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.themeid.client.model.DownloadThemeIdResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.theme.themeid.model.BMThemePathToId;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

/**
 * Scheduler service that downloads theme ids for given theme path from Mediapool server
 */
@Service
public class BMGetThemeIdSchedulerService extends AbstractSchedulerService {

    private final BMDownloadThemeIdClient bmDownloadThemeIdClient;

    private final BMThemePathToId bmThemePathToId;

    public BMGetThemeIdSchedulerService(final BMDownloadThemeIdClient bmDownloadThemeIdClient,
                                        final BMThemePathToId bmThemePathToId) {
        this.bmDownloadThemeIdClient = bmDownloadThemeIdClient;
        this.bmThemePathToId = bmThemePathToId;
    }

    //@PostConstruct
    public void init() {
        String bmGetThemeIdCronExpression = appConfig.getBmGetThemeIdCronExpression();
        if (bmGetThemeIdCronExpression != null) {
            jobSchedule(new CronTrigger(bmGetThemeIdCronExpression));
        }
    }

    @Override
    protected void scheduled() {
        for (final String themePath : bmThemePathToId.keySet()) {
            final BMTheme theme = new BMTheme();
            theme.setThemePath(themePath);
            final DownloadThemeIdResponse response = bmDownloadThemeIdClient.downloadThemeId(theme);
            if (response.isStatus()) {
                bmThemePathToId.put(themePath, String.valueOf(response.getTheme().getId()));
            } else {
                final String message = String.format("Can not download theme id from theme path [%s], with messages [%s] and warnings [%s]", themePath, response.getErrorAsString(), response.getWarningsAsString());
                final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, null, null, null);
                reportsRepository.save(reportsEntity);
                logger.error(message);
            }
        }
    }
}
