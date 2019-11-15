package MediaPoolMalBridge.service.MAL.kits;

import MediaPoolMalBridge.clients.MAL.kits.client.MALGetKitsClient;
import MediaPoolMalBridge.clients.MAL.kits.client.model.MALGetKitsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.kits.MALKits;
import MediaPoolMalBridge.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class MALGetKitsService extends AbstractService {

    private final MALGetKitsClient getKitsClient;

    private final MALKits malKits;

    public MALGetKitsService( final MALGetKitsClient getKitsClient,
                              final MALKits malKits )
    {
        this.getKitsClient = getKitsClient;
        this.malKits = malKits;
    }

    public void downloadKits()
    {
        final RestResponse<MALGetKitsResponse> response = getKitsClient.download();
        if( !response.isSuccess() ||
            response.getResponse() == null ||
            response.getResponse().getKits() == null )
        {
            logger.error( "Can not get kits response {}", GSON.toJson(response));
            return;
        }

        response.getResponse().getKits().forEach( malKit -> malKits.put( malKit.getName(), malKit.getKitId() ) );
    }
}
