package MediaPoolMalBridge.service.Bridge.excelcreator.affiliation;

import MediaPoolMalBridge.service.Bridge.excelcreator.AbstractBridgeUniqueExcelService;
import org.springframework.stereotype.Service;

@Service
public class BridgeCreateAffiliateExcelUniqueThreadService extends AbstractBridgeUniqueExcelService {

    @Override
    protected void run()
    {
        writeToFile( "AFFILIATES_CODE.xls" );
    }
}
