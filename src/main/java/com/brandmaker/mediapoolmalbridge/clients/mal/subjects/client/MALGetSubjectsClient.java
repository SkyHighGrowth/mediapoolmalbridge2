package com.brandmaker.mediapoolmalbridge.clients.mal.subjects.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.subjects.client.model.MALGetSubjectsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps class to MAL server end point /list_subjects.json
 */
@Component
public class MALGetSubjectsClient extends MALSingleResponseClient<Object, MALGetSubjectsResponse> {

    private static final String URL_SEGMENT = "list_subjects.json";

    public MALGetSubjectsClient() {
        super(MALGetSubjectsResponse.class);
    }

    public RestResponse<MALGetSubjectsResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}