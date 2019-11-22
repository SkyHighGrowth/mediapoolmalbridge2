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
    public void scheduler()
    {
        if( isRunScheduler() )
        {
            taskSchedulerWrapper.getTaskScheduler().schedule( this::upload, new CronTrigger(Constants.CRON_DAILY_TRIGGGER_EXPRESSION ) );
        }
    }

    public void upload( )
    {
        bridgeUploadExcelFilesService.upload();
    }
}
