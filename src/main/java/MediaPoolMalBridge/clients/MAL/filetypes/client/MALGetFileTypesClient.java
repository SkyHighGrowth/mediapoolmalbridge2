package MediaPoolMalBridge.clients.MAL.filetypes.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.filetypes.client.model.MALGetFileTypesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetFileTypesClient extends MALSingleResponseClient<Object, MALGetFileTypesResponse> {

    private static final String urlSegment = "list_file_types.json";

    public MALGetFileTypesClient() {
        super(MALGetFileTypesResponse.class);
    }

    public RestResponse<MALGetFileTypesResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}

