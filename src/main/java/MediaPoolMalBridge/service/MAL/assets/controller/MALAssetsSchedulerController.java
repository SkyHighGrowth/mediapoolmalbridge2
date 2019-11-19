package MediaPoolMalBridge.service.MAL.assets.controller;

import MediaPoolMalBridge.service.MAL.assets.MALAssetsSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALAssetsSchedulerController {

    private final MALAssetsSchedulerService malAssetsSchedulerService;

    public MALAssetsSchedulerController(final MALAssetsSchedulerService malAssetsSchedulerService) {
        this.malAssetsSchedulerService = malAssetsSchedulerService;
    }

    /**
     * updates MALAssets structure for unavailable created and updated
     */
    @GetMapping("/service/mal/updateAssets")
    public void getUpdates() {
        malAssetsSchedulerService.update();
    }

    /**
     * triggers download of MALAssets
     */
    @GetMapping("/service/mal/downloadAssets")
    public void getAssets() {
        malAssetsSchedulerService.downloadAssets();
    }
}
