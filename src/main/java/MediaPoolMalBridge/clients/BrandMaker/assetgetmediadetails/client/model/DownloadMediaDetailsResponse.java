package MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model;

import com.brandmaker.webservices.mediapool.GetMediaDetailsResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DownloadMediaDetailsResponse {

    private boolean status;

    private List<String> errors = new ArrayList<>();

    private List<String> warnings = new ArrayList<>();

    private GetMediaDetailsResult getMediaDetailsResult;

    public DownloadMediaDetailsResponse(final boolean status, final String error) {
        this.status = status;
        this.errors = Collections.singletonList(error);
    }

    public DownloadMediaDetailsResponse(final GetMediaDetailsResult result) {
        status = result.isSuccess();
        if (!result.isSuccess()) {
            errors = new ArrayList<>();
            result.getErrors().forEach(x -> errors.add(x.getError()));

            warnings = new ArrayList<>();
            result.getWarnings().forEach(x -> warnings.add(x.getWarning()));
        }
        getMediaDetailsResult = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public GetMediaDetailsResult getGetMediaDetailsResult() {
        return getMediaDetailsResult;
    }

    public void setGetMediaDetailsResult(GetMediaDetailsResult getMediaDetailsResult) {
        this.getMediaDetailsResult = getMediaDetailsResult;
    }
}
