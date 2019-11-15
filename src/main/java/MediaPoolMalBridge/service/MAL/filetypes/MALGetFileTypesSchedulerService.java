package MediaPoolMalBridge.service.MAL.filetypes;

import MediaPoolMalBridge.clients.MAL.filetypes.client.MALGetFileTypesClient;
import MediaPoolMalBridge.clients.MAL.filetypes.client.model.MALGetFileTypesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.model.MAL.filetype.MALFileTypes;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MALGetFileTypesSchedulerService extends AbstractSchedulerService {

    private final MALFileTypes malFileTypes;

    private final MALGetFileTypesClient getFileTypesClient;

    public MALGetFileTypesSchedulerService(final MALFileTypes malFileTypes,
                                           final MALGetFileTypesClient getFileTypesClient)
    {
        this.malFileTypes = malFileTypes;
        this.getFileTypesClient = getFileTypesClient;
    }

    @PostConstruct
    public void schedule()
    {
        downloadFileTypes();
        if( isRunScheduler() )
        {
            taskSchedulerWrapper.getTaskScheduler().schedule( this::downloadFileTypes, new CronTrigger( Constants.CRON_SIX_HOURS_TRIGGER_EXPRESSION ));
        }
    }

    public void downloadFileTypes()
    {
        final RestResponse<MALGetFileTypesResponse> response = getFileTypesClient.download();
        if( !response.isSuccess() ||
            response.getResponse() == null ||
            response.getResponse().getFileTypes() == null )
        {
            logger.error( "Can not download file types with response {}", GSON.toJson( response ) );
            return;
        }

        malFileTypes.update( response.getResponse() );
    }
}
