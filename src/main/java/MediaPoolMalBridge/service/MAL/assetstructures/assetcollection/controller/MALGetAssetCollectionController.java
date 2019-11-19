package MediaPoolMalBridge.service.MAL.assetstructures.assetcollection.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.assetcollection.MALGetAssetCollectionService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetAssetCollectionController {

    private MALGetAssetCollectionService getAssetCollectionService;

    public MALGetAssetCollectionController(final MALGetAssetCollectionService getAssetCollectionService) {
        this.getAssetCollectionService = getAssetCollectionService;
    }

    @GetMapping("/service/mal/getCollections")
    public void getCollections() {
        getAssetCollectionService.download();
    }
}
