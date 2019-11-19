package MediaPoolMalBridge.service.BrandMaker.assetmetadata.controller;

import MediaPoolMalBridge.service.BrandMaker.assetmetadata.BMExchangeAssetMetadataSchedulerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BMUploadAssetMetadataController {

    private BMExchangeAssetMetadataSchedulerService bmExchangeAssetMetadataSchedulerService;

    public BMUploadAssetMetadataController(final BMExchangeAssetMetadataSchedulerService bmExchangeAssetMetadataSchedulerService) {
        this.bmExchangeAssetMetadataSchedulerService = bmExchangeAssetMetadataSchedulerService;
    }

    @GetMapping("/service/bm/exchangeMetadata")
    public void uploadMetadata() {
        bmExchangeAssetMetadataSchedulerService.exchangeMetadata();
    }
}
