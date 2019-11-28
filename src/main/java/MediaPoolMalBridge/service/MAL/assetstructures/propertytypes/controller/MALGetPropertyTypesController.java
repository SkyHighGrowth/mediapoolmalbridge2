package MediaPoolMalBridge.service.MAL.assetstructures.propertytypes.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.propertytypes.MALGetPropertyTypesUniqueThreadService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetPropertyTypesController {

    private final MALGetPropertyTypesUniqueThreadService getPropertyTypesService;

    public MALGetPropertyTypesController(final MALGetPropertyTypesUniqueThreadService getPropertyTypesService )
    {
        this.getPropertyTypesService = getPropertyTypesService;
    }

    @GetMapping( "/appStatus/mal/propertyTypes" )
    public void download() {
        getPropertyTypesService.start();
    }
}
