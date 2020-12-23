package com.brandmaker.mediapoolmalbridge.service.bridge.excelcreator;

import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.bridge.excelcreator.excelfilesserver6_5.BridgeCreateExcelXSSFFileUniqueThreadService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service which triggers execution of excel creation
 */
@Service
@DependsOn("BridgeDatabaseNormalizerService")
public class BridgeCreateExcelFileSchedulerService extends AbstractSchedulerService {

    private final BridgeCreateExcelXSSFFileUniqueThreadService bridgeCreateExcelXSSFFileUniqueThreadService;

    public BridgeCreateExcelFileSchedulerService(final BridgeCreateExcelXSSFFileUniqueThreadService bridgeCreateExcelXSSFFileUniqueThreadService) {
        this.bridgeCreateExcelXSSFFileUniqueThreadService = bridgeCreateExcelXSSFFileUniqueThreadService;
    }

    @PostConstruct
    public void scheduleExcelFileCreation() {
        String bridgeExcelFilesCronExpression = appConfig.getBridgeExcelFilesCronExpression();
        if (bridgeExcelFilesCronExpression != null) {
            jobScheduleExcel(new CronTrigger(bridgeExcelFilesCronExpression));
        }
    }

    public void jobScheduleExcel(CronTrigger trigger) {
        if (!appConfig.getAppConfigData().isDoNotCreateExcelFiles()) {
            jobSchedule(trigger);
        }
    }

    @Override
    public void scheduled() {
        if (!appConfig.getAppConfigData().isDoNotCreateExcelFiles()) {
            logger.info("Creation of excel files started...");
            bridgeCreateExcelXSSFFileUniqueThreadService.start();
            logger.info("Creation of excel files ended...");
        }
    }
}
