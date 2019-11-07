package MediaPoolMalBridge.clients.MAL.filetypes.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.filetypes.client.model.MALGetFileTypesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetFileTypesClient extends MALSingleResponseClient<Object, MALGetFileTypesResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetFileTypesClient.class);

    private String urlSegmest = "list_file_types.json";

    public MALGetFileTypesClient() { }

    public RestResponse<MALGetFileTypesResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET );
    }
}

