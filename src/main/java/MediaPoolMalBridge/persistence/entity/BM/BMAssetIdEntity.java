package MediaPoolMalBridge.persistence.entity.BM;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table( name = "bm_asset" )
public class BMAssetIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column( name = "id" )
    private long id;

    @Column( name = "bm_asset_id" )
    private String bmAssetId;

    @CreationTimestamp
    @Column( name = "created" )
    private LocalDateTime created;

    @OneToMany( mappedBy = "bmAssetIdEntity")
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
