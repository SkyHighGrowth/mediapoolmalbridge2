package MediaPoolMalBridge.persistence.repository.Bridge;

import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Reposiotory class for {@link AssetEntity}
 */
public interface AssetRepository extends CrudRepository<AssetEntity, Long> {

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

    List<AssetEntity> findAllByTransferringAssetStatusAndUpdatedIsAfter( final TransferringAssetStatus transferringAssetStatus, final LocalDateTime updated, final Pageable page );

    @Query( "select ae " +
            "from AssetEntity ae " +
            "join fetch ae.assetJsonedValuesEntity ajve " +
            "where ae.transferringAssetStatus = :transferringAssetStatus " +
            "and ae.updated > :updated" )
    List<AssetEntity> findAllByTransferringAssetStatusAndUpdatedIsAfterFetch( final TransferringAssetStatus transferringAssetStatus, final LocalDateTime updated, final Pageable page );

    List<AssetEntity> findAllByMalAssetOperationAndUpdatedIsAfter( final MALAssetOperation arg1, final LocalDateTime arg2, final Pageable page );

    Long countByMalAssetIdAndAssetTypeAndTransferringAssetStatusNotAndUpdatedIsBeforeAndUpdatedIsAfter(
            final String assetId, final MALAssetType assetType, final TransferringAssetStatus transferringAssetStatus, final LocalDateTime updatedAssetTimeStamp, final LocalDateTime updated);

    List<AssetEntity> findAllByUpdatedIsAfter(final LocalDateTime updated, final Pageable page );

    List<AssetEntity> findAllByMalAssetIdAndAssetTypeAndUpdatedIsAfter(final String malAssetId, final MALAssetType malAssetType, final LocalDateTime updated );
}
