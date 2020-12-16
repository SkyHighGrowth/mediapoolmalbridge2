package com.brandmaker.mediapoolmalbridge.service.mal.assets;

import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.created.MALCollectCreatedAssetsUniqueThreadSinceService;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.deleted.MALCollectDeletedAssetsUniqueThreadSinceService;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.modified.MALCollectModifiedAssetUniqueThreadSinceService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Class that collects common fields and methods for MAL Scheduling services
 */
@Service
public class MALAssetsSchedulerService extends AbstractMALSchedulerService {

    private final MALCollectCreatedAssetsUniqueThreadSinceService malCollectCreatedAssetsUniqueThreadSinceService;

    private final MALCollectModifiedAssetUniqueThreadSinceService malCollectModifiedAssetUniqueThreadSinceService;

    private final MALCollectDeletedAssetsUniqueThreadSinceService malCollectDeletedAssetsUniqueThreadSinceService;

    public MALAssetsSchedulerService(final MALCollectCreatedAssetsUniqueThreadSinceService malCollectCreatedAssetsUniqueThreadSinceService,
                                     final MALCollectModifiedAssetUniqueThreadSinceService malCollectModifiedAssetUniqueThreadSinceService,
                                     final MALCollectDeletedAssetsUniqueThreadSinceService malCollectDeletedAssetsUniqueThreadSinceService) {
        this.malCollectCreatedAssetsUniqueThreadSinceService = malCollectCreatedAssetsUniqueThreadSinceService;
        this.malCollectModifiedAssetUniqueThreadSinceService = malCollectModifiedAssetUniqueThreadSinceService;
        this.malCollectDeletedAssetsUniqueThreadSinceService = malCollectDeletedAssetsUniqueThreadSinceService;
    }

    @PostConstruct
    public void init() {
        jobSchedule(new CronTrigger(appConfig.getMalAssetCronExpression()));
    }

    @Override
    public void scheduled() {
        final String since = getMidnightMalLookInThePast();
        logger.info("Collecting created assets started");
        malCollectCreatedAssetsUniqueThreadSinceService.setSince( since );
        malCollectCreatedAssetsUniqueThreadSinceService.start();
        logger.info("Collecting created assets ended");
        logger.info("Collecting modified assets started");
        malCollectModifiedAssetUniqueThreadSinceService.setSince( since );
        malCollectModifiedAssetUniqueThreadSinceService.start();
        logger.info("Collecting modified assets ended");
        logger.info("Collecting deleted assets started");
        malCollectDeletedAssetsUniqueThreadSinceService.setSince( since );
        malCollectDeletedAssetsUniqueThreadSinceService.start();
        logger.info("Collecting deleted assets ended");
    }
}
