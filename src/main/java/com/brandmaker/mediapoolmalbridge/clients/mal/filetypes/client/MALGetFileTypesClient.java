package com.brandmaker.mediapoolmalbridge.clients.mal.filetypes.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.filetypes.client.model.MALGetFileTypesResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point list_file_types.json
 */
@Component
public class MALGetFileTypesClient extends MALSingleResponseClient<Object, MALGetFileTypesResponse> {

    private static final String URL_SEGMENT = "list_file_types.json";

    public MALGetFileTypesClient() {
        super(MALGetFileTypesResponse.class);
    }

    public RestResponse<MALGetFileTypesResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}

