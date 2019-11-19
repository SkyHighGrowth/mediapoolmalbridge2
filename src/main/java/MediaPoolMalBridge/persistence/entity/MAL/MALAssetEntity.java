package MediaPoolMalBridge.persistence.entity.MAL;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAsset;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALModifiedPropertyPhotoAsset;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(indexes = {@Index(columnList = "asset_id, asset_type")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"asset_id", "asset_type"})})
public class MALAssetEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "asset_id")
    private String assetId;

    @Column(name = "bm_asset_id")
    private String bmAssetId;

    @Column( name = "property_id" )
    private String propertyId;

    @Column( name = "mal_asset_type_id" )
    private String malAssetTypeId;

    @Column( name = "color_id" )
    private String malColorId;

    @Column(name = "asset_type")
    @Enumerated(EnumType.STRING)
    private MALAssetType assetType;

    @Column(name = "mal_modified_property_photo_json", columnDefinition="LONGTEXT")
    private String malModifiedPropertyPhotoAssetJson;

    @Column(name = "mal_get_asset_json", columnDefinition="LONGTEXT")
    private String malGetAssetJson;

    @Column(name = "mal_get_unavailable_asset_json", columnDefinition="LONGTEXT")
    private String malGetUnavailableAssetJson;

    @Column(name = "medium_photo_file_name")
    private String mediumPhotoFileName;

    @Column(name = "jpg_photo_file_name")
    private String jpgPhotoFileName;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "logo_jpg_file_name")
    private String logoJPGFileName;

    @Column(name = "logo_png_file_name")
    private String logoPNGFileName;

    @Column(name = "mal_states_repetitions")
    private int malStatesRepetitions = 0;

    @Column(name = "transferring_asset_status")
    @Enumerated(EnumType.STRING)
    private TransferringAssetStatus transferringAssetStatus = TransferringAssetStatus.INVALID;

    @Column(name = "transferring_mal_connection_status")
    @Enumerated(EnumType.STRING)
    private TransferringMALConnectionAssetStatus transferringMALConnectionAssetStatus = TransferringMALConnectionAssetStatus.INVALID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getBmAssetId() {
        return bmAssetId;
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

    public void setBmAssetId(String bmAssetId) {
        this.bmAssetId = bmAssetId;
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

    public String getMediumPhotoFileName() {
        return mediumPhotoFileName;
    }

    public void setMediumPhotoFileName(String mediumPhotoFileName) {
        this.mediumPhotoFileName = mediumPhotoFileName;
    }

    public String getJpgPhotoFileName() {
        return jpgPhotoFileName;
    }

    public void setJpgPhotoFileName(String jpgPhotoFileName) {
        this.jpgPhotoFileName = jpgPhotoFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLogoJPGFileName() {
        return logoJPGFileName;
    }

    public void setLogoJPGFileName(String logoJPGFileName) {
        this.logoJPGFileName = logoJPGFileName;
    }

    public String getLogoPNGFileName() {
        return logoPNGFileName;
    }

    public void setLogoPNGFileName(String logoPNGFileName) {
        this.logoPNGFileName = logoPNGFileName;
    }

    public int getMalStatesRepetitions() {
        return malStatesRepetitions;
    }

    public void setMalStatesRepetitions(int malStatesRepetitions) {
        this.malStatesRepetitions = malStatesRepetitions;
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

    public static MALAssetEntity fromMALGetAsset(final MALGetAsset malGetAsset, final MALAssetType malAssetType) {
        final MALAssetEntity malAsset = new MALAssetEntity();
        malAsset.setAssetType(malAssetType);
        malAsset.setAssetId(malGetAsset.getAssetId());
        malAsset.setMALGetAsset(malGetAsset);
        return malAsset;
    }

    public static MALAssetEntity fromMALGetUnavailableAsset(final MALGetUnavailableAsset malGetUnavailableAsset, final MALAssetType malAssetType) {
        final MALAssetEntity malAsset = new MALAssetEntity();
        malAsset.setAssetId(malGetUnavailableAsset.getAssetId());
        malAsset.setAssetType(malAssetType);
        malAsset.setMALGetUnavailableAsset(malGetUnavailableAsset);
        return malAsset;
    }
}
