package MediaPoolMalBridge.service.MAL.assetstructures.propertytypes.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.propertytypes.MALGetPropertyTypesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MALGetPropertyTypesController {

    private final MALGetPropertyTypesService getPropertyTypesService;

    public MALGetPropertyTypesController(final MALGetPropertyTypesService getPropertyTypesService )
    {
        this.getPropertyTypesService = getPropertyTypesService;
    }

    @GetMapping( "/appStatus/mal/propertyTypes" )
    public void download() {
        getPropertyTypesService.start();
    }
}
