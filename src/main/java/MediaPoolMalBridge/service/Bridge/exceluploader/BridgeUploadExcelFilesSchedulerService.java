package MediaPoolMalBridge.service.Bridge.exceluploader;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.Bridge.exceluploader.upload.BridgeUploadExcelFilesUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service which triggers execution of {@link BridgeUploadExcelFilesSchedulerService}
 */
@Service
public class BridgeUploadExcelFilesSchedulerService extends AbstractSchedulerService {

    private final BridgeUploadExcelFilesUniqueThreadService bridgeUploadExcelFilesUniqueThreadService;

    public BridgeUploadExcelFilesSchedulerService( final BridgeUploadExcelFilesUniqueThreadService bridgeUploadExcelFilesUniqueThreadService)
    {
        this.bridgeUploadExcelFilesUniqueThreadService = bridgeUploadExcelFilesUniqueThreadService;
    }

    @PostConstruct
    public void init()
    {
        taskSchedulerWrapper.getTaskScheduler().schedule( this::run, new CronTrigger( appConfig.getBridgeUploadExcelFilesCronExpression() ) );
    }

    public void scheduled( )
    {
        bridgeUploadExcelFilesUniqueThreadService.start();
    }
}
