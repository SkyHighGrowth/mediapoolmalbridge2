package com.brandmaker.mediapoolmalbridge.service.brandmaker.assetmetadata;

import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.assetmetadata.download.BMFireDownloadAssetsMetadataUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.assetmetadata.upload.BMFireUploadAssetsMetadataUniqueThreadService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service for metadata exchange between Mediapool server and MALToMediapoolBridge
 */
@Service
@DependsOn("BridgeDatabaseNormalizerService")
public class BMExchangeAssetMetadataSchedulerService extends AbstractSchedulerService {

    private final BMFireUploadAssetsMetadataUniqueThreadService bmFireUploadAssetsMetadataUniqueThreadService;

    private final BMFireDownloadAssetsMetadataUniqueThreadService bmFireDownloadAssetsMetadataUniqueThreadService;

    public BMExchangeAssetMetadataSchedulerService(final BMFireUploadAssetsMetadataUniqueThreadService bmFireUploadAssetsMetadataUniqueThreadService,
                                                   final BMFireDownloadAssetsMetadataUniqueThreadService bmFireDownloadAssetsMetadataUniqueThreadService) {
        this.bmFireUploadAssetsMetadataUniqueThreadService = bmFireUploadAssetsMetadataUniqueThreadService;
        this.bmFireDownloadAssetsMetadataUniqueThreadService = bmFireDownloadAssetsMetadataUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        String bmExchangeSchedulerCronExpression = appConfig.getBmExchangeSchedulerCronExpression();
        if (bmExchangeSchedulerCronExpression != null) {
            jobSchedule(new CronTrigger(bmExchangeSchedulerCronExpression));
        }
    }

    @Override
    public void scheduled() {
        logger.info("Uploading asset metadata started...");
        bmFireUploadAssetsMetadataUniqueThreadService.start();
        logger.info("Uploading asset metadata ended...");
        logger.info("Downloading assets BM data started...");
        bmFireDownloadAssetsMetadataUniqueThreadService.start();
        logger.info("Downloading assets BM data ended...");
    }
}
