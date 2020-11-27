package com.brandmaker.mediapoolmalbridge.persistence.entity.bm;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Mediapool asset id table
 */
@Entity
@Table( name = "bm_asset"
)
public class BMAssetIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column( name = "id" )
    @Expose()
    private long id;

    @Column( name = "bm_asset_id" )
    @Expose()
    private String bmAssetId;

    @CreationTimestamp
    @Column( name = "created" )
    @Expose()
    private LocalDateTime created;

    @OneToMany( mappedBy = "bmAssetIdEntity", fetch = FetchType.LAZY )
    @Expose( serialize = false, deserialize = false )
    private List<AssetEntity> assetEntities;

    public BMAssetIdEntity() {}

    public BMAssetIdEntity( final String bmAssetId ) {
        this.bmAssetId = bmAssetId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBmAssetId() {
        return bmAssetId;
    }

    public void setBmAssetId(String bmAssetId) {
        this.bmAssetId = bmAssetId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
