package com.brandmaker.mediapoolmalbridge.service.mal.assets;

import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.created.MALCollectNewBrandAssetsUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Class that collects only new brand assets from MAL
 */
@Service
public class MALNewBrandAssetsSchedulerService extends AbstractMALSchedulerService {

    private final MALCollectNewBrandAssetsUniqueThreadService malCollectNewBrandAssetsUniqueThreadService;

    public MALNewBrandAssetsSchedulerService(final MALCollectNewBrandAssetsUniqueThreadService malCollectNewBrandAssetsUniqueThreadService) {
        this.malCollectNewBrandAssetsUniqueThreadService = malCollectNewBrandAssetsUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        String malNewAssetCronExpression = appConfig.getMalNewAssetCronExpression();
        if (malNewAssetCronExpression != null) {
            jobSchedule(new CronTrigger(malNewAssetCronExpression));
        }
    }

    @Override
    protected void scheduled() {
        logger.info("Collecting new brand assets started");
        malCollectNewBrandAssetsUniqueThreadService.start();
        logger.info("Collecting new brand assets ended");
    }
}
