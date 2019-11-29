package MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import com.brandmaker.webservices.mediapool.UploadMediaResult;
import com.brandmaker.webservices.mediapool.UploadMediaVersionResult;

import java.util.ArrayList;
import java.util.Collections;

public class UploadStatus extends AbstractBMResponse {

    private String fileName;

    private String assetId;

    public UploadStatus(final UploadMediaResult result, final String assetId) {
        this.assetId = result.getMediaGuid();
        status = result.isSuccess();
        errors = Collections.singletonList(result.getError());
        warnings = new ArrayList<>();
        result.getWarnings().forEach(warning -> warnings.add(warning.getWarning()));
    }

    public UploadStatus(final UploadMediaVersionResult result, final String assetId) {
        this.assetId = assetId;
        status = result.isSuccess();
        errors = Collections.singletonList(result.getError());
        warnings = new ArrayList<>();
        result.getWarnings().forEach(warning -> warnings.add(warning.getWarning()));
    }

    public UploadStatus(final boolean success, final String errorMessage, final String assetId) {
        status = success;
        errors = Collections.singletonList(errorMessage);
        warnings = new ArrayList<>();
        this.assetId = assetId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBmAsset() {
        return assetId;
    }

    public void setBmAsset(String assetId) {
        this.assetId = assetId;
    }
}

