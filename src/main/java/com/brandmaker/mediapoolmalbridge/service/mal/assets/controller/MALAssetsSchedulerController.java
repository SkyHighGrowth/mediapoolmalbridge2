package com.brandmaker.mediapoolmalbridge.service.mal.assets.controller;

import com.brandmaker.mediapoolmalbridge.service.mal.assets.MALAssetsSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.MALDownloadAssetSchedulerService;
import com.brandmaker.mediapoolmalbridge.tasks.TaskSchedulerWrapper;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller that triggers {@link MALAssetsSchedulerService} and {@link MALDownloadAssetSchedulerService}
 * servises, calling get methods only trigger enly oxecution of this schedulers using current no {@link TaskSchedulerWrapper}
 * is involved
 */
@RestController
@Profile("enable controllers")
public class MALAssetsSchedulerController {

    private final MALAssetsSchedulerService assetsSchedulerService;

    private final MALDownloadAssetSchedulerService malDownloadAssetSchedulerService;

    public MALAssetsSchedulerController(final MALAssetsSchedulerService assetsSchedulerService,
                                        final MALDownloadAssetSchedulerService malDownloadAssetSchedulerService) {
        this.assetsSchedulerService = assetsSchedulerService;
        this.malDownloadAssetSchedulerService = malDownloadAssetSchedulerService;
    }

    @GetMapping("/service/mal/updateAssets")
    public void getUpdates() {
        assetsSchedulerService.scheduled();
    }

    @GetMapping("/service/mal/downloadAssets")
    public void getAssets() {
        malDownloadAssetSchedulerService.scheduled( );
    }
}
