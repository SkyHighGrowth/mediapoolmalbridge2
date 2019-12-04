package MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of {@link MALGetModifiedPropertyPhotoResponse}
 */
public class MALModifiedPropertyPhotoAsset {

    @SerializedName("asset_id")
    private String assetId;

    @SerializedName("download_url")
    private String downloadUrl;

    @SerializedName("jpg_download_url")
    private String jpgDownloadUrl;

    @SerializedName("medium_download_url")
    private String mediumDownloadUrl;

    private String caption;

    private String subject;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getJpgDownloadUrl() {
        return jpgDownloadUrl;
    }

    public void setJpgDownloadUrl(String jpgDownloadUrl) {
        this.jpgDownloadUrl = jpgDownloadUrl;
    }

    public String getMediumDownloadUrl() {
        return mediumDownloadUrl;
    }

    public void setMediumDownloadUrl(String mediumDownloadUrl) {
        this.mediumDownloadUrl = mediumDownloadUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFilename() {
        //TODO what do to here
        return "what_now";
    }
}
