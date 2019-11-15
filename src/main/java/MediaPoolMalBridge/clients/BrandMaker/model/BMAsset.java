package MediaPoolMalBridge.clients.BrandMaker.model;

import MediaPoolMalBridge.model.asset.TransferringAsset;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;

public class BMAsset extends TransferringAsset {

    private String assetId;

    private String fileName;

    private UploadMetadataArgument uploadMetadataArgument;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UploadMetadataArgument getUploadMetadataArgument() {
        return uploadMetadataArgument;
    }

    public void setUploadMetadataArgument(UploadMetadataArgument uploadMetadataArgument) {
        this.uploadMetadataArgument = uploadMetadataArgument;
    }
}
