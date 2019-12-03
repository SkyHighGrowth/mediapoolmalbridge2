package MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import com.brandmaker.webservices.mediapool.UploadMediaResult;
import com.brandmaker.webservices.mediapool.UploadMediaVersionResult;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Wraps results of MediapoolWebServicePort.uploadMediaVersionAsStream
 */
public class UploadStatus extends AbstractBMResponse {

    /**
     * Mediapool asset id
     */
    private String assetId;

    /**
     * Constructs from response of MediapoolWebServicePort.uploadMediaAsStream used when we create asset
     * @param result
     * @param assetId
     */
    public UploadStatus(final UploadMediaResult result, final String assetId) {
        this.assetId = result.getMediaGuid();
        status = result.isSuccess();
        errors = Collections.singletonList(result.getError());
        warnings = new ArrayList<>();
        result.getWarnings().forEach(warning -> warnings.add(warning.getWarning()));
    }

    /**
     * Constructs from response of MediapoolWebServicePort.uploadMediaVersionAsStream used when we upload asset version
     * @param result
     * @param assetId
     */
    public UploadStatus(final UploadMediaVersionResult result, final String assetId) {
        this.assetId = assetId;
        status = result.isSuccess();
        errors = Collections.singletonList(result.getError());
        warnings = new ArrayList<>();
        result.getWarnings().forEach(warning -> warnings.add(warning.getWarning()));
    }

    /**
     * Constructs form erroneous response of MediapoolWebServicePort.uploadMediaAsStream and
     * MediapoolWebServicePort.uploadMediaVersionAsStream
     * @param success - always false
     * @param errorMessage
     * @param assetId
     */
    public UploadStatus(final boolean success, final String errorMessage, final String assetId) {
        status = success;
        errors = Collections.singletonList(errorMessage);
        warnings = new ArrayList<>();
        this.assetId = assetId;
    }

    public String getBmAsset() {
        return assetId;
    }

    public void setBmAsset(String assetId) {
        this.assetId = assetId;
    }
}

