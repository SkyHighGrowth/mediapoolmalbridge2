package MediaPoolMalBridge.service.MAL.assetstructures.assetcolor.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.assetcolor.MALGetAssetColorService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetAssetColorController {

    private final MALGetAssetColorService getAssetColorService;

    public MALGetAssetColorController(final MALGetAssetColorService getAssetColorService) {
        this.getAssetColorService = getAssetColorService;
    }

    @GetMapping("/service/mal/getCollors")
    public void getColors() {
        getAssetColorService.start();
    }
}
