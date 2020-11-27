package com.brandmaker.mediapoolmalbridge.service.brandmaker.assetmetadata.controller;

import com.brandmaker.mediapoolmalbridge.service.brandmaker.assetmetadata.BMExchangeAssetMetadataSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller which is used to trigger {@link BMExchangeAssetMetadataSchedulerService} scheduler service
 */
@RestController
@Profile( "enable controllers" )
public class BMUploadAssetMetadataController {

    private final BMExchangeAssetMetadataSchedulerService bmExchangeAssetMetadataSchedulerService;

    public BMUploadAssetMetadataController(final BMExchangeAssetMetadataSchedulerService bmExchangeAssetMetadataSchedulerService) {
        this.bmExchangeAssetMetadataSchedulerService = bmExchangeAssetMetadataSchedulerService;
    }

    @GetMapping("/service/bm/exchangeMetadata")
    public void uploadMetadata() {
        bmExchangeAssetMetadataSchedulerService.scheduled();
    }
}
