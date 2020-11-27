package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.controller;

import com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.MALGetAssetStructureSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that triggers download of asset structures from MAL server
 */
@RestController
@Profile( "enable controllers" )
public class MALAssetStructuresController {

    private final MALGetAssetStructureSchedulerService malGetAssetStructureSchedulerService;

    public MALAssetStructuresController(final MALGetAssetStructureSchedulerService malGetAssetStructureSchedulerService) {
        this.malGetAssetStructureSchedulerService = malGetAssetStructureSchedulerService;
    }

    @GetMapping("/service/mal/assetStructures")
    public void getAssetStructures() {
        malGetAssetStructureSchedulerService.scheduled();
    }
}
