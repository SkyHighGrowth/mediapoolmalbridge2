package MediaPoolMalBridge.service.Bridge.excelcreator;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.Bridge.excelcreator.affiliation.BridgeCreateAffiliateExcelService;
import MediaPoolMalBridge.service.Bridge.excelcreator.propertystructure.BridgeCreatePropertyStructureExcelService;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.PostConstruct;

public class BridgeCreateExcelFileSchedulerService extends AbstractSchedulerService {

    private BridgeCreateAffiliateExcelService bridgeCreateAffiliateExcelService;

    private BridgeCreatePropertyStructureExcelService bridgeCreatePropertyStructureExcelService;

    public BridgeCreateExcelFileSchedulerService(final BridgeCreateAffiliateExcelService bridgeCreateAffiliateExcelService,
                                        final BridgeCreatePropertyStructureExcelService bridgeCreatePropertyStructureExcelService)
    {
        this.bridgeCreateAffiliateExcelService = bridgeCreateAffiliateExcelService;
        this.bridgeCreatePropertyStructureExcelService = bridgeCreatePropertyStructureExcelService;
    }

    @PostConstruct
    public void scheduleExcelFileCreation()
    {
        if( isRunScheduler() )
        {
            taskSchedulerWrapper.getTaskScheduler().schedule( this::createExcelFiles, new CronTrigger(Constants.CRON_DAILY_TRIGGGER_EXPRESSION) );
        }
    }

    public void createExcelFiles()
    {
        bridgeCreateAffiliateExcelService.createAffiliateExcel();
        bridgeCreatePropertyStructureExcelService.createPropertyStructuresExcelFiles();
    }
}
