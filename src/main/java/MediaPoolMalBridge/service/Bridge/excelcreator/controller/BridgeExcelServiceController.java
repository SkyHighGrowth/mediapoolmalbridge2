package MediaPoolMalBridge.service.Bridge.excelcreator.controller;

import MediaPoolMalBridge.service.Bridge.excelcreator.affiliation.BridgeCreateAffiliateExcelUniqueThreadService;
import MediaPoolMalBridge.service.Bridge.excelcreator.propertystructure.BridgeCreatePropertyStructureExcelUniqueThreadService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class BridgeExcelServiceController {

    private BridgeCreateAffiliateExcelUniqueThreadService bridgeCreateAffiliateExcelUniqueThreadService;

    private BridgeCreatePropertyStructureExcelUniqueThreadService bridgeCreatePropertyStructureExcelUniqueThreadService;

    public BridgeExcelServiceController(final BridgeCreateAffiliateExcelUniqueThreadService bridgeCreateAffiliateExcelUniqueThreadService,
                                        final BridgeCreatePropertyStructureExcelUniqueThreadService bridgeCreatePropertyStructureExcelUniqueThreadService)
    {
        this.bridgeCreateAffiliateExcelUniqueThreadService = bridgeCreateAffiliateExcelUniqueThreadService;
        this.bridgeCreatePropertyStructureExcelUniqueThreadService = bridgeCreatePropertyStructureExcelUniqueThreadService;
    }

    @GetMapping( "/service/app/affiliateCodes" )
    public void createAffiliateCodes()
    {
        bridgeCreateAffiliateExcelUniqueThreadService.start();
    }

    @GetMapping( "/service/app/createStructures" )
    public void createStructures( ) { bridgeCreatePropertyStructureExcelUniqueThreadService.start(); }
}
