package MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import com.brandmaker.webservices.mediapool.UploadMediaResult;
import com.brandmaker.webservices.mediapool.UploadMediaVersionResult;

import java.util.ArrayList;
import java.util.Collections;

public class UploadStatus extends AbstractBMResponse {

    private String fileName;

    private String bmAssetId;

    public UploadStatus(final UploadMediaResult result, final String bmAssetId) {
        this.bmAssetId = result.getMediaGuid();
        status = result.isSuccess();
        errors = Collections.singletonList(result.getError());
        warnings = new ArrayList<>();
        result.getWarnings().forEach(warning -> warnings.add(warning.getWarning()));
    }

    public UploadStatus(final UploadMediaVersionResult result, final String bmAssetId) {
        this.bmAssetId = bmAssetId;
        status = result.isSuccess();
        errors = Collections.singletonList(result.getError());
        warnings = new ArrayList<>();
        result.getWarnings().forEach(warning -> warnings.add(warning.getWarning()));
    }

    public UploadStatus(final boolean success, final String errorMessage, final String bmAssetId) {
        status = success;
        errors = Collections.singletonList(errorMessage);
        warnings = new ArrayList<>();
        this.bmAssetId = bmAssetId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBmAsset() {
        return bmAssetId;
    }

    public void setBmAsset(String bmAssetId) {
        this.bmAssetId = bmAssetId;
    }
}

