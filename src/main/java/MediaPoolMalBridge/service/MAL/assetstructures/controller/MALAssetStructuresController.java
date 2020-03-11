package MediaPoolMalBridge.service.MAL.assetstructures.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.MALGetAssetStructureSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that triggers download of asset structures from MAL server
 */
@RestController
@Profile( "enable controllers" )
public class MALAssetStructuresController {

    private MALGetAssetStructureSchedulerService malGetAssetStructureSchedulerService;

    public MALAssetStructuresController(final MALGetAssetStructureSchedulerService malGetAssetStructureSchedulerService) {
        this.malGetAssetStructureSchedulerService = malGetAssetStructureSchedulerService;
    }

    @GetMapping("/service/mal/assetStructures")
    public void getAssetStructures() {
        malGetAssetStructureSchedulerService.scheduled();
    }
}
