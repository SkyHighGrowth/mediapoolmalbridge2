package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for fetching asset files data from api/syncs
 */
public class MALFilesData {
    @SerializedName("asset_file_id")
    public String assetFileId;
    @SerializedName("asset_id")
    public String assetId;
    @SerializedName("type")
    public String type;
    @SerializedName("url")
    public String url;
    @SerializedName("width")
    public Integer width;
    @SerializedName("height")
    public Integer height;
    @SerializedName("metadata")
    public Object metadata;

    public String getAssetFileId() {
        return assetFileId;
    }

    public void setAssetFileId(String assetFileId) {
        this.assetFileId = assetFileId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }
}
