package MediaPoolMalBridge.persistence.repository.Bridge;

import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends CrudRepository<AssetEntity, Long> {
    Slice<AssetEntity> findAllByTransferringAssetStatusOrTransferringAssetStatusAndUpdatedIsAfter(
            final TransferringAssetStatus arg1, final TransferringAssetStatus arg2, final LocalDateTime arg3, final Pageable page);

    @Query( "select ae " +
            "from AssetEntity ae " +
            "where ae.propertyId = :propertyId " +
            "and ae.assetTypeId = :assetTypeId " +
            "and ae.colorId like ':colorId%' " +
            "and ae.transferringAssetStatus = :transferringAssetStatus " +
            "and ae.updated > :arg5 " +
            "order by ae.updated desc")
    List<AssetEntity> findAssetDetails(final String propertyId, final String assetTypeId, final String colorId, final TransferringAssetStatus transferringAssetStatus, final LocalDateTime arg5 );

    @Query( "select ae " +
            "from AssetEntity ae " +
            "where ae.propertyId = :propertyId " +
            "and ae.assetTypeId = :assetTypeId " +
            "and ae.updated > :updated " +
            "order by ae.updated desc")
    List<AssetEntity> findPropertyAssets( final String propertyId, final String assetTypeId, final LocalDateTime updated );

    Slice<AssetEntity> findAllByTransferringAssetStatusOrTransferringAssetStatusOrTransferringAssetStatusAndUpdatedIsAfter(
            final TransferringAssetStatus arg1, final TransferringAssetStatus arg2, final TransferringAssetStatus arg3, final LocalDateTime arg4, final Pageable page );

    Slice<AssetEntity> findAllByTransferringAssetStatusAndUpdatedIsAfter( final TransferringAssetStatus transferringAssetStatus, final LocalDateTime updated, final Pageable page );

    List<AssetEntity> findByMalAssetIdAndAssetType(final String assetId, final MALAssetType assetType);

    Optional<AssetEntity> findByMalAssetIdAndAssetTypeAndUpdatedIsAfter(final String assetId, final MALAssetType assetType, final LocalDateTime updated);

    /*@Query("select mae " +
            "from MALAssetEntity mae " +
            "where mae.transferringAssetStatus = :arg1 or " +
            "mae.transferringAssetStatus = :arg2" )*/
    Slice<AssetEntity> findAllByMalAssetOperationOrMalAssetOperation(final MALAssetOperation arg1, final MALAssetOperation arg2, final Pageable arg3  );

    Slice<AssetEntity> findAllByMalAssetOperationAndUpdatedIsAfter( final MALAssetOperation arg1, final LocalDateTime arg2, final Pageable page );

    Slice<AssetEntity> findAllByMalAssetIdAndAssetTypeAndTransferringAssetStatus( final String malAssetId, final MALAssetType malAssetType, final TransferringAssetStatus transferringAssetStatus );

    List<AssetEntity> findAllByMalAssetIdAndAssetType( final String malAssetId, final MALAssetType malAssetType );
}
