package MediaPoolMalBridge.service.BrandMaker.assetmetadata.controller;

import MediaPoolMalBridge.service.BrandMaker.assetmetadata.BMExchangeAssetMetadataSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.assetmetadata.download.BMDownloadAssetMetadataService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile( "dev" )
public class BMUploadAssetMetadataController {

    private BMExchangeAssetMetadataSchedulerService bmExchangeAssetMetadataSchedulerService;
    private BMDownloadAssetMetadataService bmDownloadAssetMetadataService;

    public BMUploadAssetMetadataController(final BMExchangeAssetMetadataSchedulerService bmExchangeAssetMetadataSchedulerService) {
        this.bmExchangeAssetMetadataSchedulerService = bmExchangeAssetMetadataSchedulerService;
    }

    @GetMapping("/service/bm/exchangeMetadata")
    public void uploadMetadata() {
        bmExchangeAssetMetadataSchedulerService.scheduled();
    }
}
