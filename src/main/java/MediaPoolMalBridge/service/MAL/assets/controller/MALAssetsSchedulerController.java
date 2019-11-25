package MediaPoolMalBridge.service.MAL.assets.controller;

import MediaPoolMalBridge.service.MAL.assets.MALAssetsSchedulerService;
import MediaPoolMalBridge.service.MAL.assets.MALDownloadAssetSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALAssetsSchedulerController {

    private final MALAssetsSchedulerService malAssetsSchedulerService;

    private final MALDownloadAssetSchedulerService malDownloadAssetSchedulerService;

    public MALAssetsSchedulerController(final MALAssetsSchedulerService malAssetsSchedulerService,
                                        final MALDownloadAssetSchedulerService malDownloadAssetSchedulerService) {
        this.malAssetsSchedulerService = malAssetsSchedulerService;
        this.malDownloadAssetSchedulerService = malDownloadAssetSchedulerService;
    }

    /**
     * updates MALAssets structure for unavailable created and updated
     */
    @GetMapping("/service/mal/updateAssets")
    public void getUpdates() {
        malAssetsSchedulerService.scheduled();
    }

    /**
     * triggers download of MALAssets
     */
    @GetMapping("/service/mal/downloadAssets")
    public void getAssets() {
        malDownloadAssetSchedulerService.scheduled( );
    }
}
