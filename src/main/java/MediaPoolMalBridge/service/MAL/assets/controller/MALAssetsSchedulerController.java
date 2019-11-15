package MediaPoolMalBridge.service.MAL.assets.controller;

import MediaPoolMalBridge.service.MAL.assets.MALAssetsSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile( "dev" )
public class MALAssetsSchedulerController {

    private final MALAssetsSchedulerService malAssetsSchedulerService;

    public MALAssetsSchedulerController(final MALAssetsSchedulerService malAssetsSchedulerService) {
        this.malAssetsSchedulerService = malAssetsSchedulerService;
    }

    @GetMapping("/mal/assetsScheduler/update")
    public void getUpdates()
    {
        malAssetsSchedulerService.update();
    }

    @GetMapping("/mal/assetsScheduler/downloadAssets")
    public void getAssets()
    {
        malAssetsSchedulerService.downloadAssets();
    }
}
