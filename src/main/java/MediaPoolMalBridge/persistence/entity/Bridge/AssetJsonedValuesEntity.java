package MediaPoolMalBridge.persistence.entity.Bridge;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "asset_jsoned_values" )
public class AssetJsonedValuesEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "id" )
    private long id;

    @Column( name = "mal_get_asset_json", columnDefinition="LONGTEXT" )
    private String malGetAssetJson;

    @Column( name = "mal_get_unavailable_asset_json", columnDefinition="LONGTEXT" )
    private String malGetUnavailableAssetJson;

    @Column(name = "bm_upload_metadata_argument_json", columnDefinition="LONGTEXT")
    private String bmUploadMetadataArgumentJson;

    @OneToOne(mappedBy = "assetJsonedValuesEntity", fetch = FetchType.LAZY )
    private AssetEntity assetEntity;

    @CreationTimestamp
    @Column( name = "created" )
    private LocalDateTime created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getBmUploadMetadataArgumentJson() {
        return bmUploadMetadataArgumentJson;
    }

    public void setBmUploadMetadataArgumentJson(String bmUploadMetadataArgumentJson) {
        this.bmUploadMetadataArgumentJson = bmUploadMetadataArgumentJson;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
