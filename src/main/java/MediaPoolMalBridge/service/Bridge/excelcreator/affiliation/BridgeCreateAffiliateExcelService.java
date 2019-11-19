package MediaPoolMalBridge.service.Bridge.excelcreator.affiliation;

import MediaPoolMalBridge.service.Bridge.excelcreator.AbstractBridgeExcelService;
import org.springframework.stereotype.Service;

@Service
public class BridgeCreateAffiliateExcelService extends AbstractBridgeExcelService {

    public void createAffiliateExcel( )
    {
        writeToFile( "AFFILIATES_CODE.xls" );
    }
}
