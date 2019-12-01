package MediaPoolMalBridge.persistence.entity.Bridge;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAsset;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALModifiedPropertyPhotoAsset;
import MediaPoolMalBridge.persistence.entity.AbstractEntity;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetIdEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
import com.google.gson.annotations.Expose;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table( name = "mal_asset",
        indexes = { @Index( columnList = "mal_asset_id, mal_asset_type" ),
                    @Index( columnList = "transferring_status"),
                    @Index( columnList = "transferring_status, mal_asset_operation, updated") } )
public class AssetEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column( name = "mal_asset_operation" )
    @Enumerated( EnumType.STRING )
    private MALAssetOperation malAssetOperation;

    @Column(name = "mal_asset_id")
    private String malAssetId;

    @Column(name = "mal_asset_type")
    @Enumerated( EnumType.STRING )
    private MALAssetType assetType;

    @ManyToOne
    @JoinColumn( name = "bm_asset_id", nullable = false )
    @Expose( serialize = false, deserialize = false )
    private BMAssetIdEntity bmAssetIdEntity;

    @Column( name = "property_id" )
    private String propertyId;

    @Column( name = "marsha_code" )
    private String marshaCode;

    @Column( name = "brand_id" )
    private String brandId;

    @Column( name = "mal_asset_type_id" )
    private String assetTypeId;

    @Column( name = "color_id" )
    private String colorId;

    @Column(name = "caption")
    private String caption;

    @Column( name = "file_name_on_disc" )
    private String fileNameOnDisc;

    @Column( name = "file_name_in_mal" )
    private String fileNameInMal;

    @Column( name = "url" )
    private String url;

    @Column( name = "mal_states_repetitions" )
    private int malStatesRepetitions = 0;

    @Column( name = "transferring_status" )
    @Enumerated( EnumType.STRING )
    private TransferringAssetStatus transferringAssetStatus = TransferringAssetStatus.INVALID;

    @CreationTimestamp
    @Column( name = "created" )
    private LocalDateTime created;

    @UpdateTimestamp
    @Column( name = "updated" )
    private LocalDateTime updated;

    @Column( name = "mal_last_modified" )
    private String malLastModified;

    @Column( name = "mal_created" )
    private String malCreated;

    @Column( name = "mal_modified_property_photo_json", columnDefinition="LONGTEXT" )
    private String malModifiedPropertyPhotoAssetJson;

    @Column( name = "mal_get_asset_json", columnDefinition="LONGTEXT" )
    private String malGetAssetJson;

    @Column( name = "mal_get_unavailable_asset_json", columnDefinition="LONGTEXT" )
    private String malGetUnavailableAssetJson;

    @Column(name = "bm_upload_metadata_argument", columnDefinition="LONGTEXT")
    private String bmUploadMetadataArgumentJson;

    @Column( name = "mal_md5_hash" )
    private String malMd5Hash;

    @Column( name = "bm_md5_hash" )
    private String bmMd5Hash;

    public AssetEntity() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMalModifiedPropertyPhotoAssetJson() {
        return malModifiedPropertyPhotoAssetJson;
    }

    public void setMalModifiedPropertyPhotoAssetJson(String malModifiedPropertyPhotoAssetJson) {
        this.malModifiedPropertyPhotoAssetJson = malModifiedPropertyPhotoAssetJson;
    }

    public String getMalGetAssetJson() {
        return malGetAssetJson;
    }

    public void setMalGetAssetJson(String malGetAssetJson) {
        this.malGetAssetJson = malGetAssetJson;
    }

    public String getMalGetUnavailableAssetJson() {
        return malGetUnavailableAssetJson;
    }

    public void setMalGetUnavailableAssetJson(String malGetUnavailableAssetJson) {
        this.malGetUnavailableAssetJson = malGetUnavailableAssetJson;
    }

    public MALModifiedPropertyPhotoAsset getMALModifiedPropertyPhotoAsset() {
        if (malModifiedPropertyPhotoAssetJson != null) {
            return GSON.fromJson(malModifiedPropertyPhotoAssetJson, MALModifiedPropertyPhotoAsset.class);
        }
        return null;
    }

    public void setMALModifiedPropertyPhotoAsset(final MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset) {
        this.malModifiedPropertyPhotoAssetJson = GSON.toJson(malModifiedPropertyPhotoAsset);
        this.malMd5Hash = DigestUtils.md5Hex( malModifiedPropertyPhotoAssetJson );
    }

    public MALGetAsset getMALGetAsset() {
        if (malGetAssetJson != null) {
            return GSON.fromJson(this.malGetAssetJson, MALGetAsset.class);
        }
        return null;
    }

    public void setMALGetAsset(final MALGetAsset malGetAsset) {
        this.malGetAssetJson = GSON.toJson(malGetAsset);
        this.malMd5Hash = DigestUtils.md5Hex( malGetAssetJson );
    }

    public MALGetUnavailableAsset getMALGetUnavailableAsset() {
        if (malGetUnavailableAssetJson != null) {
            return GSON.fromJson(malGetUnavailableAssetJson, MALGetUnavailableAsset.class);
        }
        return null;
    }

    public void setMALGetUnavailableAsset(final MALGetUnavailableAsset malGetUnavailableAsset) {
        this.malGetUnavailableAssetJson = GSON.toJson(malGetUnavailableAsset);
    }

    public static AssetEntity fromMALGetModifiedPropertyPhotos(final MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset, final MALAssetType assetType) {
        final AssetEntity asset = new AssetEntity();
        asset.setMalAssetId(malModifiedPropertyPhotoAsset.getAssetId());
        asset.setAssetType(assetType);
        asset.setMALModifiedPropertyPhotoAsset(malModifiedPropertyPhotoAsset);
        return asset;
    }

    public AssetEntity fromMALGetUnavailableAsset(final MALGetUnavailableAsset malGetUnavailableAsset, final MALAssetType assetType) {
        final AssetEntity asset = new AssetEntity();
        asset.setMalAssetId(malGetUnavailableAsset.getAssetId());
        asset.setAssetType(assetType);
        asset.setMALGetUnavailableAsset(malGetUnavailableAsset);
        return asset;
    }

    public void setBmUploadMetadataArgument(final UploadMetadataArgument bmUploadMetadataArgument) {
        bmUploadMetadataArgumentJson = GSON.toJson(bmUploadMetadataArgument);
    }

    public UploadMetadataArgument getBmUploadMetadataArgument() {
        if (bmUploadMetadataArgumentJson != null) {
            return GSON.fromJson(bmUploadMetadataArgumentJson, UploadMetadataArgument.class);
        }
        return null;
    }

    public MALAssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(MALAssetType assetType) {
        this.assetType = assetType;
    }


    public MALAssetOperation getMalAssetOperation() {
        return malAssetOperation;
    }

    public void setMalAssetOperation(MALAssetOperation malAssetOperation) {
        this.malAssetOperation = malAssetOperation;
    }

    public String getMalAssetId() {
        return malAssetId;
    }

    public void setMalAssetId(String malAssetId) {
        this.malAssetId = malAssetId;
    }

    public String getBmAssetId() {
        return bmAssetIdEntity.getBmAssetId();
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getMarshaCode() {
        return marshaCode;
    }

    public void setMarshaCode(String marshaCode) {
        this.marshaCode = marshaCode;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getMalAssetTypeId() {
        return assetTypeId;
    }

    public void setMalAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getFileNameOnDisc() {
        return fileNameOnDisc;
    }

    public void setFileNameOnDisc(String fileNameOnDisc) {
        this.fileNameOnDisc = fileNameOnDisc;
    }

    public String getFileNameInMal() {
        return fileNameInMal;
    }

    public void setFileNameInMal(String fileNameInMal) {
        this.fileNameInMal = fileNameInMal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMalStatesRepetitions() {
        return malStatesRepetitions;
    }

    public void setMalStatesRepetitions(int malStatesRepetitions) {
        this.malStatesRepetitions = malStatesRepetitions;
    }

    public void increaseMalStatesRepetitions() {
        ++malStatesRepetitions;
    }

    public TransferringAssetStatus getTransferringAssetStatus() {
        return transferringAssetStatus;
    }

    public void setTransferringAssetStatus(TransferringAssetStatus transferringAssetStatus) {
        this.transferringAssetStatus = transferringAssetStatus;
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

    public String getBmUploadMetadataArgumentJson() {
        return bmUploadMetadataArgumentJson;
    }

    public void setBmUploadMetadataArgumentJson(String bmUploadMetadataArgumentJson) {
        this.bmUploadMetadataArgumentJson = bmUploadMetadataArgumentJson;
    }

    public String getMalMd5Hash() {
        return malMd5Hash;
    }

    public void setMalMd5Hash(String malMd5Hash) {
        this.malMd5Hash = malMd5Hash;
    }

    public String getBmMd5Hash() {
        return bmMd5Hash;
    }

    public void setBmMd5Hash(String bmMd5Hash) {
        this.bmMd5Hash = bmMd5Hash;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public BMAssetIdEntity getBmAssetIdEntity() {
        return bmAssetIdEntity;
    }

    public void setBmAssetIdEntity(BMAssetIdEntity bmAssetIdEntity) {
        this.bmAssetIdEntity = bmAssetIdEntity;
    }
}
