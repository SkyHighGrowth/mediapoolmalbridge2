package com.brandmaker.mediapoolmalbridge.service.bridge.database.assetresolver;

import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BridgeDatabaseAssetResolverSchedulerService extends AbstractSchedulerService {

    private final BridgeDatabaseAssetResolverUniqueThreadService bridgeDatabaseAssetResolverUniqueThreadService;

    public BridgeDatabaseAssetResolverSchedulerService(final BridgeDatabaseAssetResolverUniqueThreadService bridgeDatabaseAssetResolverUniqueThreadService ) {
        this.bridgeDatabaseAssetResolverUniqueThreadService = bridgeDatabaseAssetResolverUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        jobSchedule(new CronTrigger(appConfig.getBridgeAssetResolverCronExpression()));
    }

    @Override
    public void scheduled() {
        logger.info("Searching for updated assets started");
        bridgeDatabaseAssetResolverUniqueThreadService.start();
        logger.info("Searching for updated assets ended");
    }
}
