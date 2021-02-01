package com.brandmaker.mediapoolmalbridge.persistence.entity.bridge;

import com.brandmaker.mediapoolmalbridge.clients.mal.model.MALAssetType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.AbstractEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bm.BMAssetIdEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.MALAssetOperation;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Table where asset entities are stored
 */
@Entity
@Table(name = "asset",
        indexes = {@Index(columnList = "property_id, mal_asset_type, color_id, transferring_status, updated"),
                @Index(columnList = "property_id, mal_asset_type, updated"),
                @Index(columnList = "transferring_status, brand_id, updated"),
                @Index(columnList = "mal_asset_id, mal_asset_type, transferring_status, updated"),
                @Index(columnList = "updated"),
                @Index(columnList = "mal_asset_id, mal_asset_type, updated"),
                @Index(columnList = "mal_asset_id, mal_asset_type"),
                @Index(columnList = "updated, transferring_status")})
public class AssetEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Expose()
    private long id;

    @Column(name = "mal_asset_operation")
    @Enumerated(EnumType.STRING)
    @Expose()
    private MALAssetOperation malAssetOperation;

    @Column(name = "mal_asset_id")
    @Expose()
    private String malAssetId;

    @Column(name = "mal_asset_type")
    @Enumerated(EnumType.STRING)
    @Expose()
    private MALAssetType assetType;

    @ManyToOne
    @JoinColumn(name = "bm_asset_id", referencedColumnName = "id", nullable = false)
    @Expose(serialize = false, deserialize = false)
    @JsonIgnore
    private BMAssetIdEntity bmAssetIdEntity;

    @Column(name = "property_id")
    @Expose()
    private String propertyId;

    @Column(name = "marsha_code")
    @Expose()
    private String marshaCode;

    @Column(name = "brand_id")
    @Expose()
    private String brandId;

    @Column(name = "mal_asset_type_id")
    @Expose()
    private String assetTypeId;

    @Column(name = "color_id")
    @Expose()
    private String colorId;

    @Column(name = "caption")
    @Expose()
    private String caption;

    @Column(name = "file_name_on_disc")
    @Expose()
    private String fileNameOnDisc;

    @Column(name = "file_name_in_mal")
    @Expose()
    private String fileNameInMal;

    @Column(name = "url")
    @Expose()
    private String url;

    @Column(name = "mal_states_repetitions")
    @Expose()
    private int malStatesRepetitions = 0;

    @Column(name = "transferring_status")
    @Enumerated(EnumType.STRING)
    @Expose()
    private TransferringAssetStatus transferringAssetStatus = TransferringAssetStatus.INVALID;

    @CreationTimestamp
    @Column(name = "created")
    @Expose()
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    @Expose()
    private LocalDateTime updated;

    @Column(name = "mal_last_modified")
    @Expose()
    private String malLastModified;

    @Column(name = "mal_created")
    @Expose()
    private String malCreated;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "asset_jsoned_values_id", referencedColumnName = "id", nullable = false)
    @Expose(serialize = false, deserialize = false)
    @JsonIgnore
    private AssetJsonedValuesEntity assetJsonedValuesEntity;

    @Column(name = "mal_md5_hash")
    @Expose()
    private String malMd5Hash;

    @Column(name = "bm_md5_hash")
    @Expose()
    @JsonIgnore
    private String bmMd5Hash;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return assetJsonedValuesEntity.getBmUploadMetadataArgumentJson();
    }

    public void setBmUploadMetadataArgumentJson(String bmUploadMetadataArgumentJson) {
        assetJsonedValuesEntity.setBmUploadMetadataArgumentJson(bmUploadMetadataArgumentJson);
    }

    public AssetJsonedValuesEntity getAssetJsonedValuesEntity() {
        return assetJsonedValuesEntity;
    }

    public void setAssetJsonedValuesEntity(AssetJsonedValuesEntity assetJsonedValuesEntity) {
        this.assetJsonedValuesEntity = assetJsonedValuesEntity;
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
