package MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import com.brandmaker.webservices.mediapool.UploadMetadataResult;

import java.util.ArrayList;
import java.util.Collections;

public class UploadMetadataStatus extends AbstractBMResponse {

    public UploadMetadataStatus(final UploadMetadataResult uploadMetadataResult) {
        status = uploadMetadataResult.isSuccess();
        errors = new ArrayList<>();
        if (uploadMetadataResult.getErrors() != null) {
            uploadMetadataResult.getErrors()
                    .forEach(x -> errors.add(x.getError()));
        }
        warnings = new ArrayList<>();
        if (uploadMetadataResult.getWarnings() != null) {
            uploadMetadataResult.getWarnings()
                    .forEach(x -> warnings.add(x.getWarning()));
        }
    }

    public UploadMetadataStatus(final boolean status, final String error) {
        this.status = status;
        errors = Collections.singletonList(error);
        warnings = new ArrayList<>();
    }

    public String getMessage() {
        String message = "";
        if (!errors.isEmpty()) {
            message += "Error: " + String.join("; Error: ", errors) + "; ";
        }
        if (!warnings.isEmpty()) {
            message += "Warning: " + String.join("; Warning: ", warnings) + ";";
        }
        return message;
    }
}
