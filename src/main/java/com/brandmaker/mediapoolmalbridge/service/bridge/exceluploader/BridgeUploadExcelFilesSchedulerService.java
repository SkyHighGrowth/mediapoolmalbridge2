package com.brandmaker.mediapoolmalbridge.service.bridge.exceluploader;

import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.exceluploader.upload.BridgeUploadExcelFilesUniqueThreadService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service which triggers execution of {@link BridgeUploadExcelFilesSchedulerService}
 */
@Service
@DependsOn("BridgeDatabaseNormalizerService")
public class BridgeUploadExcelFilesSchedulerService extends AbstractSchedulerService {

    private final BridgeUploadExcelFilesUniqueThreadService bridgeUploadExcelFilesUniqueThreadService;

    public BridgeUploadExcelFilesSchedulerService(final BridgeUploadExcelFilesUniqueThreadService bridgeUploadExcelFilesUniqueThreadService) {
        this.bridgeUploadExcelFilesUniqueThreadService = bridgeUploadExcelFilesUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        String bridgeUploadExcelFilesCronExpression = appConfig.getBridgeUploadExcelFilesCronExpression();
        if (bridgeUploadExcelFilesCronExpression != null) {
            jobSchedule(new CronTrigger(bridgeUploadExcelFilesCronExpression));
        }
    }

    public void scheduled() {
        if (appConfig.getAppConfigData().isUseSftp()) {
            bridgeUploadExcelFilesUniqueThreadService.start();
        }
    }
}
