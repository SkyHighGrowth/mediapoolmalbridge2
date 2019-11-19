package MediaPoolMalBridge.clients.BrandMaker.model;

import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAsset;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;

public class BMAssetToBeDeleted extends TransferringAsset {

    private String assetId;

    private String malAssetId;

    private String fileName;

    private MALAssetType malAssetType;

    private String malLastModified;

    private String malCreated;

    private UploadMetadataArgument uploadMetadataArgument;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getMalAssetId() {
        return malAssetId;
    }

    public void setMalAssetId(String malAssetId) {
        this.malAssetId = malAssetId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MALAssetType getMalAssetType() {
        return malAssetType;
    }

    public String getMalLastModified() {
        return malLastModified;
    }

    public void setMalLastModified(String malLastModified) {
        this.malLastModified = malLastModified;
    }

    public String getMalCreated() {
        return malCreated;
    }

    public void setMalCreated(String malCreated) {
        this.malCreated = malCreated;
    }

    public void setMalAssetType(MALAssetType malAssetType) {
        this.malAssetType = malAssetType;
    }

    public UploadMetadataArgument getUploadMetadataArgument() {
        return uploadMetadataArgument;
    }

    public void setUploadMetadataArgument(UploadMetadataArgument uploadMetadataArgument) {
        this.uploadMetadataArgument = uploadMetadataArgument;
    }
}
