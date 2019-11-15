package MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.BMAsset;
import com.brandmaker.webservices.mediapool.UploadMediaResult;
import com.brandmaker.webservices.mediapool.UploadMediaVersionResult;

import java.util.ArrayList;
import java.util.List;

public class UploadStatus {

    private String fileName;

    private BMAsset bmAsset;

    private boolean status;

    private String error;

    private List<String> warnings;

    public UploadStatus(final UploadMediaResult result, final BMAsset bmAsset ) {
        this.bmAsset = bmAsset;
        status = result.isSuccess();
        error = result.getError();
        warnings = new ArrayList<>();
        result.getWarnings().forEach(warning -> warnings.add(warning.getWarning()));
    }

    public UploadStatus(final UploadMediaVersionResult result, final BMAsset bmAsset) {
        this.bmAsset = bmAsset;
        status = result.isSuccess();
        error = result.getError();
        warnings = new ArrayList<>();
        result.getWarnings().forEach(warning -> warnings.add(warning.getWarning()));
    }

    public UploadStatus(final boolean success, final String errorMessage, final BMAsset bmAsset ) {
        status = success;
        error = errorMessage;
        warnings = new ArrayList<>();
        this.bmAsset = bmAsset;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public BMAsset getBmAsset() {
        return bmAsset;
    }

    public void setBmAsset(BMAsset bmAsset) {
        this.bmAsset = bmAsset;
    }
}

