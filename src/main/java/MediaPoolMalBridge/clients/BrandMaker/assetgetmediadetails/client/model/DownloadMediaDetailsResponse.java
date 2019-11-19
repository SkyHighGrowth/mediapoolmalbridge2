package MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import com.brandmaker.webservices.mediapool.GetMediaDetailsResult;

import java.util.ArrayList;
import java.util.Collections;

public class DownloadMediaDetailsResponse extends AbstractBMResponse {

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

    public GetMediaDetailsResult getGetMediaDetailsResult() {
        return getMediaDetailsResult;
    }

    public void setGetMediaDetailsResult(GetMediaDetailsResult getMediaDetailsResult) {
        this.getMediaDetailsResult = getMediaDetailsResult;
    }
}
