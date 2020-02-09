package MediaPoolMalBridge.service.Bridge.excelcreator;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.Bridge.excelcreator.excelfilesserver6_2.affiliation.BridgeCreateAffiliateExcelUniqueThreadService;
import MediaPoolMalBridge.service.Bridge.excelcreator.excelfilesserver6_2.propertystructure.BridgeCreatePropertyStructureExcelUniqueThreadService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service which triggers execution of excel creation
 */
@Service
@DependsOn( "BridgeDatabaseNormalizerService" )
public class BridgeCreateExcelFileSchedulerService extends AbstractSchedulerService {

    private BridgeCreateAffiliateExcelUniqueThreadService bridgeCreateAffiliateExcelUniqueThreadService;

    private BridgeCreatePropertyStructureExcelUniqueThreadService bridgeCreatePropertyStructureExcelUniqueThreadService;

    public BridgeCreateExcelFileSchedulerService(final BridgeCreateAffiliateExcelUniqueThreadService bridgeCreateAffiliateExcelUniqueThreadService,
                                        final BridgeCreatePropertyStructureExcelUniqueThreadService bridgeCreatePropertyStructureExcelUniqueThreadService)
    {
        this.bridgeCreateAffiliateExcelUniqueThreadService = bridgeCreateAffiliateExcelUniqueThreadService;
        this.bridgeCreatePropertyStructureExcelUniqueThreadService = bridgeCreatePropertyStructureExcelUniqueThreadService;
    }

    @PostConstruct
    public void scheduleExcelFileCreation()
    {
        taskSchedulerWrapper.getTaskScheduler().schedule( this::run, new CronTrigger(appConfig.getBridgeExcelFilesCronExpression()) );
    }

    @Override
    public void scheduled()
    {
        bridgeCreateAffiliateExcelUniqueThreadService.start();
        bridgeCreatePropertyStructureExcelUniqueThreadService.start();
    }
}
