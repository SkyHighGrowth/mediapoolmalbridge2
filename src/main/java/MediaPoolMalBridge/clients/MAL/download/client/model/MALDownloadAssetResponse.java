package MediaPoolMalBridge.clients.MAL.download.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.download.client.MALDownloadAssetClient}
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
