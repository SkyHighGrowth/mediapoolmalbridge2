package MediaPoolMalBridge.service.Bridge.excelcreator;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.Bridge.excelcreator.excelfilesserver6_5.BridgeCreateExcelXSSFFileUniqueThreadService;
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

    private final BridgeCreateExcelXSSFFileUniqueThreadService bridgeCreateExcelXSSFFileUniqueThreadService;

    public BridgeCreateExcelFileSchedulerService(final BridgeCreateExcelXSSFFileUniqueThreadService bridgeCreateExcelXSSFFileUniqueThreadService)
    {
        this.bridgeCreateExcelXSSFFileUniqueThreadService = bridgeCreateExcelXSSFFileUniqueThreadService;
    }

    @PostConstruct
    public void scheduleExcelFileCreation()
    {
        if( !appConfig.isDoNotCreateExcelFiles() ) {
            taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getBridgeExcelFilesCronExpression()));
        }
    }

    @Override
    public void scheduled()
    {
        if( !appConfig.isDoNotCreateExcelFiles() ) {
            logger.info("Creation of excel files started...");
            bridgeCreateExcelXSSFFileUniqueThreadService.start();
            logger.info("Creation of excel files ended...");
        }
    }
}
