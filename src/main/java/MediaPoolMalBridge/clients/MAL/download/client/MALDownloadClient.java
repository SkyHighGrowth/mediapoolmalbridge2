package MediaPoolMalBridge.clients.MAL.download.client;

import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class MALDownloadClient<RESPONSE_TYPE> {

    final RestTemplate restTemplate;

    private MALDownloadClient( @Value( "DownloadRestTemplate" ) final RestTemplate restTemplate )
    {
        this.restTemplate = restTemplate;
    }

    public <RESPONSE_TYPE> RestResponse<RESPONSE_TYPE> download( )
    {
        return new RestResponse<RESPONSE_TYPE>( "success" );
    }
}
