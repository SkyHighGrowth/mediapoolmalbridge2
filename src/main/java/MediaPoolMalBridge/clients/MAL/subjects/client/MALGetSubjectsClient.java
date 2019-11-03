package MediaPoolMalBridge.clients.MAL.subjects.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.subjects.client.model.MALGetSubjectsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MALGetSubjectsClient extends MALSingleResponseClient<Object, MALGetSubjectsResponse> {

    private String urlSegmest = "list_subjects.json";

    public MALGetSubjectsClient() {

    }

    public RestResponse<MALGetSubjectsResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}