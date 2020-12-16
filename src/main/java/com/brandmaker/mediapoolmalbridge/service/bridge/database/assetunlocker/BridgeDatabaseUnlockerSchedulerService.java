package com.brandmaker.mediapoolmalbridge.service.bridge.database.assetunlocker;

import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BridgeDatabaseUnlockerSchedulerService extends AbstractSchedulerService {

    private final BridgeDatabaseUnlockerUniqueThreadService bridgeDatabaseUnlockerUniqueThreadService;

    public BridgeDatabaseUnlockerSchedulerService( final BridgeDatabaseUnlockerUniqueThreadService bridgeDatabaseUnlockerUniqueThreadService ) {
        this.bridgeDatabaseUnlockerUniqueThreadService = bridgeDatabaseUnlockerUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        jobSchedule(new CronTrigger(appConfig.getBridgeAssetOnBoardingCronExpression()));
    }

    @Override
    public void scheduled() {
        logger.info("Searching updated assets started");
        bridgeDatabaseUnlockerUniqueThreadService.start();
        logger.info("Searching updated assets ended");
    }
}
