package MediaPoolMalBridge.service.Bridge.excelcreator.controller;

import MediaPoolMalBridge.service.Bridge.excelcreator.affiliation.BridgeCreateAffiliateExcelService;
import MediaPoolMalBridge.service.Bridge.excelcreator.propertystructure.BridgeCreatePropertyStructureExcelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BridgeExcelServiceController {

    private BridgeCreateAffiliateExcelService bridgeCreateAffiliateExcelService;

    private BridgeCreatePropertyStructureExcelService bridgeCreatePropertyStructureExcelService;

    public BridgeExcelServiceController(final BridgeCreateAffiliateExcelService bridgeCreateAffiliateExcelService,
                                        final BridgeCreatePropertyStructureExcelService bridgeCreatePropertyStructureExcelService)
    {
        this.bridgeCreateAffiliateExcelService = bridgeCreateAffiliateExcelService;
        this.bridgeCreatePropertyStructureExcelService = bridgeCreatePropertyStructureExcelService;
    }

    @GetMapping( "/service/app/affiliateCodes" )
    public void createAffiliateCodes()
    {
        bridgeCreateAffiliateExcelService.createAffiliateExcel();
    }

    @GetMapping( "/service/app/createStructures" )
    public void createStructures( ) { bridgeCreatePropertyStructureExcelService.createPropertyStructuresExcelFiles(); }
}
