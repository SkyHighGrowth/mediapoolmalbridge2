package com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetgetmediadetails.client.model;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetgetmediadetails.client.BMDownloadMediaDetailsClient;
import com.brandmaker.webservices.mediapool.GetMediaDetailsResult;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class which holds the response of {@link BMDownloadMediaDetailsClient}
 */
public class DownloadMediaDetailsResponse extends AbstractBMResponse {

    /**
     * Class which holds the response of MediapoolWebServicePort.getMediaDetails
     */
    private GetMediaDetailsResult getMediaDetailsResult;

    /**
     * Error state constructor
     * @param status - this argument is always false
     * @param error
     */
    public DownloadMediaDetailsResponse(final boolean status, final String error) {
        this.status = status;
        this.errors = Collections.singletonList(error);
    }

    /**
     * Constructs object using regular response from MedaipoolWebServicePort.getMediaDetails
     * and keeps it for future usage as needed
     * @param result
     */
    public DownloadMediaDetailsResponse(final GetMediaDetailsResult result) {
        status = result.isSuccess();
        if (!result.isSuccess()) {
            errors = new ArrayList<>();
            result.getErrors().forEach(x -> errors.add(x.getError()));

            warnings = new ArrayList<>();
            result.getWarnings().forEach(x -> warnings.add(x.getWarning()));
        }
        getMediaDetailsResult = result;
    }

    public GetMediaDetailsResult getGetMediaDetailsResult() {
        return getMediaDetailsResult;
    }

    public void setGetMediaDetailsResult(GetMediaDetailsResult getMediaDetailsResult) {
        this.getMediaDetailsResult = getMediaDetailsResult;
    }
}
