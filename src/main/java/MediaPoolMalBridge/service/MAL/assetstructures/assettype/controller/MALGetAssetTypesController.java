package MediaPoolMalBridge.service.MAL.assetstructures.assettype.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.assettype.MALGetAssetTypeUniqueThreadService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetAssetTypesController {

    private final MALGetAssetTypeUniqueThreadService getAssetTypeService;

    public MALGetAssetTypesController(final MALGetAssetTypeUniqueThreadService getAssetTypeService) {
        this.getAssetTypeService = getAssetTypeService;
    }

    @GetMapping("/service/mal/getAssetTypes")
    public void download() {
        getAssetTypeService.start();
    }
}
