package MediaPoolMalBridge.service.MAL.assetstructures.assetbrand.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.assetbrand.MALGetAssetBrandService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetAssetBrandController {

    private final MALGetAssetBrandService getAssetBrandService;

    public MALGetAssetBrandController(final MALGetAssetBrandService getAssetBrandService) {
        this.getAssetBrandService = getAssetBrandService;
    }

    @GetMapping("/service/mal/getBrands")
    public void getBrands() {
        getAssetBrandService.download();
    }
}
