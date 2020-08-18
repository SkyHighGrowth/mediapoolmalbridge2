package MediaPoolMalBridge.persistence.repository.Bridge;

import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

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
            "and ae.colorId = :colorId " +
            "and ae.transferringAssetStatus = :transferringAssetStatus " +
            "and ae.updated > :arg5 " +
            "order by ae.updated desc")
    List<AssetEntity> findAssetDetails(final String propertyId, final String assetTypeId, final String colorId, final TransferringAssetStatus transferringAssetStatus, final LocalDateTime arg5 );

    @Query( "select ae " +
            "from AssetEntity ae " +
            "where ae.propertyId = :propertyId " +
            "and ae.assetTypeId = :assetTypeId " +
            "and ae.transferringAssetStatus = :transferringAssetStatus " +
            "and ae.updated > :updated " +
            "order by ae.updated desc")
    List<AssetEntity> findPropertyAssets( final String propertyId, final String assetTypeId, final TransferringAssetStatus transferringAssetStatus, final LocalDateTime updated );

    List<AssetEntity> findAllByBrandIdAndMalAssetIdAndAssetTypeIdAndTransferringAssetStatus(final String brandId, String malAssetId, final String assetTypeId, final TransferringAssetStatus transferringAssetStatus);

    List<AssetEntity> findAllByTransferringAssetStatusAndUpdatedIsAfter( final TransferringAssetStatus transferringAssetStatus, final LocalDateTime updated, final Pageable page );

    List<AssetEntity> findAllByTransferringAssetStatusAndBrandIdAndUpdatedIsAfter( final TransferringAssetStatus transferringAssetStatus, final String brandIds, final LocalDateTime updated, final Pageable page );
    /*
    @Query( "select ae " +
            "from AssetEntity ae " +
            "join fetch ae.assetJsonedValuesEntity ajve " +
            "where ae.transferringAssetStatus = :transferringAssetStatus " +
            "and ae.updated > :updated" )
    List<AssetEntity> findAllByTransferringAssetStatusAndUpdatedIsAfterFetch( final TransferringAssetStatus transferringAssetStatus, final LocalDateTime updated, final Pageable page );

    List<AssetEntity> findAllByMalAssetOperationAndUpdatedIsAfter( final MALAssetOperation arg1, final LocalDateTime arg2, final Pageable page );
    */

    Long countByMalAssetIdAndAssetTypeAndTransferringAssetStatusNotAndTransferringAssetStatusNotAndUpdatedIsBeforeAndUpdatedIsAfter(
            final String assetId, final MALAssetType assetType, final TransferringAssetStatus transferringAssetStatus01, final TransferringAssetStatus transferringAssetStatus02, final LocalDateTime updatedAssetTimeStamp, final LocalDateTime updated);

    List<AssetEntity> findAllByUpdatedIsAfter(final LocalDateTime updated, final Pageable page );

    List<AssetEntity> findAllByUpdatedIsAfterAndUpdatedIsBeforeAndTransferringAssetStatusIsNotAndTransferringAssetStatusIsNot(
            final LocalDateTime updatedIsAfter, final LocalDateTime updatedIsBefore, final TransferringAssetStatus not01, final TransferringAssetStatus not02, final Pageable page );

    List<AssetEntity> findAllByMalAssetIdAndAssetTypeAndUpdatedIsAfter(final String malAssetId, final MALAssetType malAssetType, final LocalDateTime updated );

    List<AssetEntity> findAllByMalAssetIdAndAssetType(final String malAssetId, final MALAssetType malAssetType );

    List<AssetEntity> findAllByUpdatedIsAfterAndUpdatedIsBeforeAndTransferringAssetStatus(
            final LocalDateTime from, final LocalDateTime to, final TransferringAssetStatus transferringAssetStatus, final Sort sort );

    @Transactional
    @Modifying
    @Query( "delete from AssetEntity ae " +
            "where ae.updated < :dateTime ")
    void deleteRowsInThePast( final LocalDateTime dateTime );

    @Query( "select ae " +
            "from AssetEntity ae " +
            "join fetch ae.assetJsonedValuesEntity ajve " +
            "where ae.brandId = :brandId " +
            "and ae.assetTypeId = :assetTypeId "+
            "and ajve.bmUploadMetadataArgumentJson like :collectionId " +
            "and ae.transferringAssetStatus = 'DONE' " +
            "and ae.bmMd5Hash is not null")
    List<AssetEntity> findAssetEntitiesByCollection(String brandId, String assetTypeId, String collectionId);
}
