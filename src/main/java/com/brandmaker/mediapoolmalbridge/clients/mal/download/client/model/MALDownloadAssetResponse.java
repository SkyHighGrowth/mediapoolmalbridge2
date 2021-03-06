package com.brandmaker.mediapoolmalbridge.clients.mal.download.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.download.client.MALDownloadAssetClient;

/**
 * Response of {@link MALDownloadAssetClient}
 */
public class MALDownloadAssetResponse extends MALAbstractResponse {

    private String absoluteFilePath;

    public String getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public void setAbsoluteFilePath(String absoluteFilePath) {
        this.absoluteFilePath = absoluteFilePath;
    }

    public boolean isSuccess() {
        return getResult() == null;
    }

    public void fromMALAbstractResposne(final MALAbstractResponse response) {
        this.setResult(response.getResult());
        this.setMessage(response.getMessage());
        this.absoluteFilePath = null;
    }

    public void fromException(final String result, final String message) {
        this.setResult(result);
        this.setMessage(message);
        this.absoluteFilePath = null;
    }
}
