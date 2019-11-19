package MediaPoolMalBridge.clients.MAL.subjects.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.subjects.client.model.MALGetSubjectsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetSubjectsClient extends MALSingleResponseClient<Object, MALGetSubjectsResponse> {

    private static final String urlSegment = "list_subjects.json";

    public MALGetSubjectsClient() {
        super(MALGetSubjectsResponse.class);
    }

    public RestResponse<MALGetSubjectsResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}