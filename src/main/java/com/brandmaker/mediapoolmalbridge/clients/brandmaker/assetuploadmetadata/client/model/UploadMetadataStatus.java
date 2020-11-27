package com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetuploadmetadata.client.model;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.webservices.mediapool.UploadMetadataResult;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Wraps response of MediapoolWebServicePort.uploadMetaData
 */
public class UploadMetadataStatus extends AbstractBMResponse {

    /**
     * Constructs from UploadMetadataResult, i.e. correct response
     * @param uploadMetadataResult
     */
    public UploadMetadataStatus(final UploadMetadataResult uploadMetadataResult) {
        status = uploadMetadataResult.isSuccess();
        errors = new ArrayList<>();
        if (uploadMetadataResult.getErrors() != null) {
            uploadMetadataResult.getErrors()
                    .forEach(x -> errors.add(x.getError()));
        }
        warnings = new ArrayList<>();
        if (uploadMetadataResult.getWarnings() != null) {
            uploadMetadataResult.getWarnings()
                    .forEach(x -> warnings.add(x.getWarning()));
        }
    }

    /**
     * Constructs from erroneous response
     * @param status - always false
     * @param error
     */
    public UploadMetadataStatus(final boolean status, final String error) {
        this.status = status;
        errors = Collections.singletonList(error);
        warnings = new ArrayList<>();
    }

    public String getMessage() {
        String message = "";
        if (!errors.isEmpty()) {
            message += "Error: " + String.join("; Error: ", errors) + "; ";
        }
        if (!warnings.isEmpty()) {
            message += "Warning: " + String.join("; Warning: ", warnings) + ";";
        }
        return message;
    }
}
