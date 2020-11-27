package com.brandmaker.mediapoolmalbridge.clients.mal.filetypes.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of {@link MALGetFileTypesResponse}
 */
public class FileType {

    @SerializedName("file_type_id")
    private String fileTypeId;

    private String name = "";

    public String getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(String fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
