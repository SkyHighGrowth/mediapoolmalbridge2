package MediaPoolMalBridge.service.BrandMaker.assets.controller;

import MediaPoolMalBridge.service.BrandMaker.assets.BMUploadAssetSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller which triggers {@link BMUploadAssetSchedulerService}
 */
@RestController
@Profile("enable controllers")
public class BMUploadAssetSchedulerController {

    private BMUploadAssetSchedulerService bmUploadAssetSchedulerService;

    public BMUploadAssetSchedulerController(final BMUploadAssetSchedulerService bmUploadAssetSchedulerService) {
        this.bmUploadAssetSchedulerService = bmUploadAssetSchedulerService;
    }

    /**
     * triggers all operations
     */
    @GetMapping("/service/bm/assets")
    public void assets() {
        bmUploadAssetSchedulerService.scheduled();
    }

    /**
     * triggers only create operation
     */
    @GetMapping("/service/bm/createAssets")
    public void createAssets() {
        bmUploadAssetSchedulerService.create();
    }

    /**
     * triggers update operation
     */
    @GetMapping("/service/bm/updateAssets")
    public void updateAssets() {
        bmUploadAssetSchedulerService.update();
    }

    /**
     * triggers update get asset id operation
     */
    @GetMapping("/service/bm/getAssetId")
    public void getAssetId() {
        bmUploadAssetSchedulerService.getAssetId();
    }

    /**
     * triggers delete operation
     */
    @GetMapping("/service/bm/deleteAssets")
    public void deleteAssets() {
        bmUploadAssetSchedulerService.delete();
    }
}
