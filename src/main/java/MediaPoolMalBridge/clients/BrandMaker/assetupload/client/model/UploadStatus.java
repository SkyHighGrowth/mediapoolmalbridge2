package MediaPoolMalBridge.clients.BrandMaker.assetupload.client.model;

import com.brandmaker.webservices.mediapool.UploadMediaVersionResult;

import java.util.ArrayList;
import java.util.List;

public class UploadStatus {

    private String fileName;

    private boolean status;

    private String error;

    private List<String> warnings;


    public UploadStatus(final UploadMediaVersionResult result, final String fileName) {
        this.fileName = fileName;
        status = result.isSuccess();
        error = result.getError();
        warnings = new ArrayList<>();
        result.getWarnings().forEach(warning -> warnings.add(warning.getWarning()));
    }

    public UploadStatus(final boolean success, final String errorMessage, final String fileName) {
        status = success;
        error = errorMessage;
        warnings = new ArrayList<>();
        this.fileName = fileName;
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
}

