package MediaPoolMalBridge.service.Bridge.excelcreator;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.Bridge.excelcreator.excelfilesserver6_5.BridgeCreateExcelFileUniqueThreadService;
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

    private final BridgeCreateExcelFileUniqueThreadService bridgeCreateExcelFileUniqueThreadService;

    public BridgeCreateExcelFileSchedulerService(final BridgeCreateExcelFileUniqueThreadService bridgeCreateExcelFileUniqueThreadService)
    {
        this.bridgeCreateExcelFileUniqueThreadService = bridgeCreateExcelFileUniqueThreadService;
    }

    @PostConstruct
    public void scheduleExcelFileCreation()
    {
        taskSchedulerWrapper.getTaskScheduler().schedule( this::run, new CronTrigger(appConfig.getBridgeExcelFilesCronExpression()) );
    }

    @Override
    public void scheduled()
    {
        bridgeCreateExcelFileUniqueThreadService.start();
    }
}
