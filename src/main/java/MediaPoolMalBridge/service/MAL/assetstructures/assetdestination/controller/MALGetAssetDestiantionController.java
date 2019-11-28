package MediaPoolMalBridge.service.MAL.assetstructures.assetdestination.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.assetdestination.MALGetAssetDestinationUniqueThreadService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetAssetDestiantionController {

    private final MALGetAssetDestinationUniqueThreadService getAssetDestinationService;

    public MALGetAssetDestiantionController(final MALGetAssetDestinationUniqueThreadService getAssetDestinationService) {
        this.getAssetDestinationService = getAssetDestinationService;
    }

    @GetMapping("/service/mal/getDestinations")
    public void getDestinations() {
        getAssetDestinationService.start();
    }
}
