package com.brandmaker.mediapoolmalbridge.clients.mal.assetsunavailable.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of the {@link MALGetUnavailableAsset}
 */
public class Urls {

    @SerializedName("thumbnail_url")
    private String thumbnailsUrl;

    @SerializedName("medium_url")
    private String mediumUrl;

    @SerializedName("large_url")
    private String largeUrl;

    @SerializedName("high_url")
    private String highUrl;

    @SerializedName("logo_jpg_url")
    private String logoJpgUrl;

    @SerializedName("logo_png_url")
    private String logoPngUrl;

    @SerializedName("xl_url")
    private String xlUrl;

    public String getThumbnailsUrl() {
        return thumbnailsUrl;
    }

    public void setThumbnailsUrl(String thumbnailsUrl) {
        this.thumbnailsUrl = thumbnailsUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public String getHighUrl() {
        return highUrl;
    }

    public void setHighUrl(String highUrl) {
        this.highUrl = highUrl;
    }

    public String getLogoJpgUrl() {
        return logoJpgUrl;
    }

    public void setLogoJpgUrl(String logoJpgUrl) {
        this.logoJpgUrl = logoJpgUrl;
    }

    public String getLogoPngUrl() {
        return logoPngUrl;
    }

    public void setLogoPngUrl(String logoPngUrl) {
        this.logoPngUrl = logoPngUrl;
    }

    public String getXlUrl() {
        return xlUrl;
    }

    public void setXlUrl(String xlUrl) {
        this.xlUrl = xlUrl;
    }
}
