package MediaPoolMalBridge.persistence.entity.MAL;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAsset;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALModifiedPropertyPhotoAsset;
import MediaPoolMalBridge.persistence.AbstractEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "mal_asset",
        indexes = { @Index( columnList = "asset_id, asset_type" ),
                    @Index( columnList = "transferring_asset_status"),
                    @Index( columnList = "transferring_mal_connection_status, transferring_asset_status")},
        uniqueConstraints = { @UniqueConstraint( columnNames = { "asset_id", "asset_type" } ) } )
public class MALAssetEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "asset_id")
    private String assetId;

    @Column(name = "asset_type")
    @Enumerated( EnumType.STRING )
    private MALAssetType assetType;

    @Column(name = "bm_asset_id")
    private String bmAssetId;

    @Column( name = "property_id" )
    private String propertyId;

    @Column( name = "mal_asset_type_id" )
    private String malAssetTypeId;

    @Column( name = "color_id" )
    private String colorId;

    @Column( name = "file_name_on_disc" )
    private String fileNameOnDisc;

    @Column( name = "file_name_in_mal" )
    private String fileNameInMal;

    @Column( name = "url" )
    private String url;

    @Column( name = "mal_states_repetitions" )
    private int malStatesRepetitions = 0;

    @Column( name = "transferring_asset_status" )
    @Enumerated( EnumType.STRING )
    private TransferringAssetStatus transferringAssetStatus = TransferringAssetStatus.INVALID;

    @Column( name = "transferring_mal_connection_status" )
    @Enumerated( EnumType.STRING )
    private TransferringMALConnectionAssetStatus transferringMALConnectionAssetStatus = TransferringMALConnectionAssetStatus.INVALID;

    @CreationTimestamp
    @Column( name = "created" )
    private LocalDateTime created;

    @UpdateTimestamp
    @Column( name = "updated" )
    private LocalDateTime updated;

    @Column( name = "mal_last_modified" )
    private String malLastModified;

    @Column( name = "mal_modified_property_photo_json", columnDefinition="LONGTEXT" )
    private String malModifiedPropertyPhotoAssetJson;

    @Column( name = "mal_get_asset_json", columnDefinition="LONGTEXT" )
    private String malGetAssetJson;

    @Column( name = "mal_get_unavailable_asset_json", columnDefinition="LONGTEXT" )
    private String malGetUnavailableAssetJson;

    @Column( name = "md5_hash" )
    private String md5Hash;

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

    public MALAssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(MALAssetType assetType) {
        this.assetType = assetType;
    }

    public String getBmAssetId() {
        return bmAssetId;
    }

    public void setBmAssetId(String bmAssetId) {
        this.bmAssetId = bmAssetId;
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
        return colorId;
    }

    public void setMalColorId(String colorId) {
        this.colorId = colorId;
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

    public int getMalStatesRepetitions() {
        return malStatesRepetitions;
    }

    public void setMalStatesRepetitions(int malStatesRepetitions) {
        this.malStatesRepetitions = malStatesRepetitions;
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

    public TransferringAssetStatus getTransferringAssetStatus() {
        return transferringAssetStatus;
    }

    public void setTransferringAssetStatus(TransferringAssetStatus transferringAssetStatus) {
        this.transferringAssetStatus = transferringAssetStatus;
    }

    public TransferringMALConnectionAssetStatus getTransferringMALConnectionAssetStatus() {
        return transferringMALConnectionAssetStatus;
    }

    public void setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus transferringMALConnectionAssetStatus) {
        if (this.transferringMALConnectionAssetStatus.equals(transferringMALConnectionAssetStatus)) {
            malStatesRepetitions++;
            return;
        }
        this.transferringMALConnectionAssetStatus = transferringMALConnectionAssetStatus;
        malStatesRepetitions = 0;
    }

    public MALModifiedPropertyPhotoAsset getMALModifiedPropertyPhotoAsset() {
        if (malModifiedPropertyPhotoAssetJson != null) {
            return GSON.fromJson(malModifiedPropertyPhotoAssetJson, MALModifiedPropertyPhotoAsset.class);
        }
        return null;
    }

    public void setMALModifiedPropertyPhotoAsset(final MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset) {
        this.malModifiedPropertyPhotoAssetJson = GSON.toJson(malModifiedPropertyPhotoAsset);
        this.md5Hash = DigestUtils.md5Hex( malModifiedPropertyPhotoAssetJson );
    }

    public MALGetAsset getMALGetAsset() {
        if (malGetAssetJson != null) {
            return GSON.fromJson(this.malGetAssetJson, MALGetAsset.class);
        }
        return null;
    }

    public void setMALGetAsset(final MALGetAsset malGetAsset) {
        this.malGetAssetJson = GSON.toJson(malGetAsset);
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

    public static MALAssetEntity fromMALGetModifiedPropertyPhotos(final MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset, final MALAssetType malAssetType) {
        final MALAssetEntity malAsset = new MALAssetEntity();
        malAsset.setAssetId(malModifiedPropertyPhotoAsset.getAssetId());
        malAsset.setAssetType(malAssetType);
        malAsset.setMALModifiedPropertyPhotoAsset(malModifiedPropertyPhotoAsset);
        return malAsset;
    }

    public MALAssetEntity fromMALGetUnavailableAsset(final MALGetUnavailableAsset malGetUnavailableAsset, final MALAssetType malAssetType) {
        final MALAssetEntity malAsset = new MALAssetEntity();
        malAsset.setAssetId(malGetUnavailableAsset.getAssetId());
        malAsset.setAssetType(malAssetType);
        malAsset.setMALGetUnavailableAsset(malGetUnavailableAsset);
        return malAsset;
    }

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }
}
