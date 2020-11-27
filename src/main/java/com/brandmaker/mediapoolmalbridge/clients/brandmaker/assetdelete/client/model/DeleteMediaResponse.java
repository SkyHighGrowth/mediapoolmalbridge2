package com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetdelete.client.model;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetdelete.client.BMDeleteAssetClient;
import com.brandmaker.webservices.mediapool.DeleteMediaResult;

/**
 * Class that represents response of {@link BMDeleteAssetClient}
 */
public class DeleteMediaResponse extends AbstractBMResponse {

    /**
     * Class which represents response of MediapoolWebServicePost.deleteMedia
     */
    private DeleteMediaResult deleteMediaResult;

    /**
     * Constructs object form DeleteMediaResult feel it with data needed
     * @param deleteMediaResult
     */
    public DeleteMediaResponse(final DeleteMediaResult deleteMediaResult) {
        this.status = deleteMediaResult.isSuccess();
        if (!deleteMediaResult.isSuccess()) {
            deleteMediaResult.getErrors().forEach(x -> errors.add(x.getError()));
            deleteMediaResult.getWarnings().forEach(x -> warnings.add(x.getWarning()));
        }
        this.deleteMediaResult = deleteMediaResult;
    }


    /**
     * Error condition constructor
     * @param status - practically always false
     * @param error - error message
     */
    public DeleteMediaResponse(final boolean status, final String error) {
        this.status = status;
        errors.add(error);
    }

    public DeleteMediaResult getDeleteMediaResult() {
        return deleteMediaResult;
    }

    public void setDeleteMediaResult(DeleteMediaResult deleteMediaResult) {
        this.deleteMediaResult = deleteMediaResult;
    }
}
