package com.brandmaker.mediapoolmalbridge.clients.mal.filetypes.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.filetypes.client.MALGetFileTypesClient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response of {@link MALGetFileTypesClient}
 */
public class MALGetFileTypesResponse {

    /**
     * List of {@link FileType} objects
     */
    @SerializedName("file_types")
    private List<FileType> fileTypes;

    public List<FileType> getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(List<FileType> fileTypes) {
        this.fileTypes = fileTypes;
    }
}
