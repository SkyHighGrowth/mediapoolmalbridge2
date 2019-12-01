package MediaPoolMalBridge.service.BrandMaker.assets.controller;

import MediaPoolMalBridge.service.BrandMaker.assets.BMUploadAssetSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class BMUploadAssetSchedulerController {

    private BMUploadAssetSchedulerService bmUploadAssetSchedulerService;

    public BMUploadAssetSchedulerController(final BMUploadAssetSchedulerService bmUploadAssetSchedulerService) {
        this.bmUploadAssetSchedulerService = bmUploadAssetSchedulerService;
    }

    @GetMapping("/service/bm/assets")
    public void assets() {
        bmUploadAssetSchedulerService.scheduled();
    }

    @GetMapping("/service/bm/createAssets")
    public void createAssets() {
        bmUploadAssetSchedulerService.create();
    }

    @GetMapping("/service/bm/updateAssets")
    public void updateAssets() {
        bmUploadAssetSchedulerService.update();
    }

    @GetMapping("/service/bm/deleteAssets")
    public void deleteAssets() {
        bmUploadAssetSchedulerService.delete();
    }
}
