package com.brandmaker.mediapoolmalbridge.service.bridge.ktistotheme;

import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.theme.download.BMDownloadThemeUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.theme.upload.BMFireUploadThemeUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.service.mal.kits.MALGetKitsUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Service which downloads Kits from MAL server
 */
@Service
public class BridgeTransferThemeSchedulerService extends AbstractSchedulerService {

    private final MALGetKitsUniqueThreadService malGetKitsUniqueThreadService;

    private final BMDownloadThemeUniqueThreadService bmDownloadThemeUniqueThreadService;

    private final BMFireUploadThemeUniqueThreadService bmFireUploadThemeUniqueThreadService;

    public BridgeTransferThemeSchedulerService(final MALGetKitsUniqueThreadService malGetKitsUniqueThreadService,
                                                final BMDownloadThemeUniqueThreadService bmDownloadThemeUniqueThreadService,
                                                final BMFireUploadThemeUniqueThreadService bmFireUploadThemeUniqueThreadService) {
        this.malGetKitsUniqueThreadService = malGetKitsUniqueThreadService;
        this.bmDownloadThemeUniqueThreadService = bmDownloadThemeUniqueThreadService;
        this.bmFireUploadThemeUniqueThreadService = bmFireUploadThemeUniqueThreadService;
    }

    @PostConstruct
    public void exchangeKitsToThemes() {
        jobSchedule(new CronTrigger(appConfig.getBridgeTransferThemeCronExpression()));
    }

    public void scheduled() {
        logger.info("Downloading kits from MAL started...");
        malGetKitsUniqueThreadService.start();
        logger.info("Downloading kits from MAL ended...");
        logger.info("Downloading themes from MAL started...");
        bmDownloadThemeUniqueThreadService.start();
        logger.info("Downloading themes from MAL ended...");
        logger.info("Uploading specific theme started...");
        bmFireUploadThemeUniqueThreadService.start();
        logger.info("Uploading specific theme ended...");
    }
}
