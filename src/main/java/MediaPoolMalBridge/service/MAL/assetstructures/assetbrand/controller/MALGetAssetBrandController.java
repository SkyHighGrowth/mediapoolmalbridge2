package MediaPoolMalBridge.service.MAL.assetstructures.assetbrand.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.assetbrand.MALGetAssetBrandUniqueThreadService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetAssetBrandController {

    private final MALGetAssetBrandUniqueThreadService getAssetBrandService;

    public MALGetAssetBrandController(final MALGetAssetBrandUniqueThreadService getAssetBrandService) {
        this.getAssetBrandService = getAssetBrandService;
    }

    @GetMapping("/service/mal/getBrands")
    public void getBrands() {
        getAssetBrandService.start();
    }
}
