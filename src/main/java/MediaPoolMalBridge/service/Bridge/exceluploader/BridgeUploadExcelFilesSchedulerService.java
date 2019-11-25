package MediaPoolMalBridge.service.Bridge.exceluploader;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.Bridge.exceluploader.upload.BridgeUploadExcelFilesService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BridgeUploadExcelFilesSchedulerService extends AbstractSchedulerService {

    private final BridgeUploadExcelFilesService bridgeUploadExcelFilesService;

    public BridgeUploadExcelFilesSchedulerService( final BridgeUploadExcelFilesService bridgeUploadExcelFilesService )
    {
        this.bridgeUploadExcelFilesService = bridgeUploadExcelFilesService;
    }

    @PostConstruct
    public void init()
    {
        taskSchedulerWrapper.getTaskScheduler().schedule( this::run, new CronTrigger( Constants.CRON_MIDNIGHT_TRIGGGER_EXPRESSION ) );
    }

    public void scheduled( )
    {
        bridgeUploadExcelFilesService.start();
    }
}
