package MediaPoolMalBridge.persistence.repository.MAL;

import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MALAssetRepository extends CrudRepository<MALAssetEntity, Long> {

    public List<MALAssetEntity> findByAssetType( final MALAssetType malAssetType );

    public MALAssetEntity findByAssetId( final String assetId );

    public List<MALAssetEntity> findByTransferringAssetStatus(final TransferringAssetStatus transferringAssetStatus );

    Optional<MALAssetEntity> findByAssetIdAndAssetType(final String assetId, final MALAssetType malAssetType);

    @Query("select mae " +
            "from MALAssetEntity mae " +
            "where mae.transferringMALConnectionAssetStatus = :arg1 or " +
            "mae.transferringAssetStatus = :arg2")
    List<MALAssetEntity> findDownloadedOrMalDeleted(final TransferringMALConnectionAssetStatus arg1, final TransferringAssetStatus arg2 );

    @Query("select mae " +
            "from MALAssetEntity mae " +
            "where mae.transferringAssetStatus = :arg1 or " +
            "mae.transferringAssetStatus = :arg2")
    List<MALAssetEntity> findMalUpdatedOrMalCreated( final TransferringAssetStatus arg1, final TransferringAssetStatus arg2 );
}
