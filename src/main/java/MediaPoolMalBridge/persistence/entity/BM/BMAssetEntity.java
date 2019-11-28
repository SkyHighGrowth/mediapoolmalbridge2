package MediaPoolMalBridge.persistence.entity.BM;

import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.persistence.entity.AbstractErroneousEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "bm_asset",
        indexes = { @Index( columnList = "transferring_bm_asset_status, updated" ),
                    @Index( columnList = "transferring_asset_status, updated" ),
                    @Index( columnList = "property_id, mal_asset_type_id, color_id, updated"),
                    @Index( columnList = "property_id, mal_asset_type_id, updated") })
public class BMAssetEntity extends AbstractErroneousEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column( name = "asset_id" )
    private String assetId;

    @Column(name = "mal_asset_id")
    private String malAssetId;

    @Column( name = "property_id" )
    private String propertyId;

    @Column( name = "mal_asset_type_id" )
    private String malAssetTypeId;

    @Column( name = "color_id" )
    private String malColorId;

    @Column(name = "caption")
    private String caption;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "mal_asset_type")
    @Enumerated(EnumType.STRING)
    private MALAssetType malAssetType;

    @Column(name = "mal_last_modified")
    private String malLastModified;

    @Column(name = "mal_created")
    private String malCreated;

    @Column(name = "upload_metadata_argument", columnDefinition="LONGTEXT")
    private String uploadMetadataArgumentJson;

    @Column(name = "bm_states_repetitions")
    private int bmStatesRepetitions = 0;

    @Column(name = "transferring_bm_asset_status")
    @Enumerated(EnumType.STRING)
    private TransferringBMConnectionAssetStatus transferringBMConnectionAssetStatus = TransferringBMConnectionAssetStatus.INVALID;

    @Column(name = "transferring_asset_status")
    @Enumerated(EnumType.STRING)
    private TransferringAssetStatus transferringConnectionAssetStatus = TransferringAssetStatus.INVALID;

    @Column(name = "md5_hash")
    private String bmHash;

    @CreationTimestamp
    @Column( name = "created" )
    private LocalDateTime created;

    @UpdateTimestamp
    @Column( name = "updated" )
    private LocalDateTime updated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getMalAssetId() {
        return malAssetId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getMalAssetTypeId() {
        return malAssetTypeId;
    }

    public void setMalAssetTypeId(String malAssetTypeId) {
        this.malAssetTypeId = malAssetTypeId;
    }

    public String getMalColorId() {
        return malColorId;
    }

    public void setMalColorId(String malColorId) {
        this.malColorId = malColorId;
    }

    public void setMalAssetId(String malAssetId) {
        this.malAssetId = malAssetId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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

    public void setMalAssetType(MALAssetType malAssetType) {
        this.malAssetType = malAssetType;
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

    public String getUploadMetadataArgumentJson() {
        return uploadMetadataArgumentJson;
    }

    public void setUploadMetadataArgumentJson(String uploadMetadataArgumentJson) {
        this.uploadMetadataArgumentJson = uploadMetadataArgumentJson;
    }

    public int getBmStatesRepetitions() {
        return bmStatesRepetitions;
    }

    public void setBmStatesRepetitions(int bmStatesRepetitions) {
        this.bmStatesRepetitions = bmStatesRepetitions;
    }

    public TransferringBMConnectionAssetStatus getTransferringBMConnectionAssetStatus() {
        return transferringBMConnectionAssetStatus;
    }

    public void setTransferringBMConnectionAssetStatus( TransferringBMConnectionAssetStatus transferringBMConnectionAssetStatus )
    {
        this.transferringBMConnectionAssetStatus = transferringBMConnectionAssetStatus;
    }

    public boolean setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus transferringBMConnectionAssetStatus, final int maxAssetOperationRetries) {
        if (this.transferringBMConnectionAssetStatus.equals(transferringBMConnectionAssetStatus)) {
            this.bmStatesRepetitions++;
            if( this.bmStatesRepetitions > maxAssetOperationRetries ) {
                this.transferringBMConnectionAssetStatus = TransferringBMConnectionAssetStatus.ERROR;
                return true;
            }
            return false;
        }
        this.bmStatesRepetitions = 0;
        this.transferringBMConnectionAssetStatus = transferringBMConnectionAssetStatus;
        return false;
    }

    public TransferringAssetStatus getTransferringConnectionAssetStatus() {
        return transferringConnectionAssetStatus;
    }

    public void setTransferringConnectionAssetStatus(TransferringAssetStatus transferringConnectionAssetStatus) {
        this.transferringConnectionAssetStatus = transferringConnectionAssetStatus;
    }

    public void setUploadMetadataArgument(final UploadMetadataArgument uploadMetadataArgument) {
        uploadMetadataArgumentJson = GSON.toJson(uploadMetadataArgument);
    }

    public UploadMetadataArgument getUploadMetadataArgument() {
        if (uploadMetadataArgumentJson != null) {
            return GSON.fromJson(uploadMetadataArgumentJson, UploadMetadataArgument.class);
        }
        return null;
    }

    public String getBmHash() {
        return bmHash;
    }

    public void setBmHash(String bmHash) {
        this.bmHash = bmHash;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
