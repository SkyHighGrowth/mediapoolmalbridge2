package MediaPoolMalBridge.persistence.repository.BM;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Transactional ( readOnly = true )
public interface BMAssetRepository extends PagingAndSortingRepository<BMAssetEntity, Long> {

    Optional<BMAssetEntity> findByAssetId(final String assetId );

    Slice<BMAssetEntity> findAllByTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusAndTransferringConnectionAssetStatusAndUpdatedIsAfter(
            final TransferringBMConnectionAssetStatus arg1, final TransferringBMConnectionAssetStatus arg2, final TransferringAssetStatus arg3, final LocalDateTime todayMidnight, final Pageable pageable );

    Slice<BMAssetEntity> findAllByTransferringBMConnectionAssetStatusOrTransferringConnectionAssetStatusAndUpdatedIsAfter(
            final TransferringBMConnectionAssetStatus arg1, final TransferringAssetStatus arg2, final LocalDateTime todayMidnight, final Pageable page );

    @Query("select bmae " +
            "from BMAssetEntity bmae " +
            "where (bmae.transferringBMConnectionAssetStatus = :arg1 or " +
            "bmae.transferringBMConnectionAssetStatus = :arg2 or " +
            "bmae.transferringBMConnectionAssetStatus = :arg3) " +
            "and updated > :arg4")
    List<BMAssetEntity> findFilUploadedOrMetadataUploadingOrMetadataUploaded(
            final TransferringBMConnectionAssetStatus arg1, final TransferringBMConnectionAssetStatus arg2, final TransferringBMConnectionAssetStatus arg3, final LocalDateTime arg4);


    //strange but original program takes into account only first asset
    @Query( "select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.propertyId = :propertyId " +
            "and bmae.malAssetTypeId = :malAssetTypeId " +
            "and bmae.malColorId like ':colorId%' " +
            "and bmae.transferringBMConnectionAssetStatus = :transferringBMConnectionAssetStatus " +
            "and updated > :arg4")
    List<BMAssetEntity> findAssetDetails( final String propertyId, final String malAssetTypeId, final String colorId, final TransferringBMConnectionAssetStatus transferringBMConnectionAssetStatus, final LocalDateTime arg4 );

    @Query( "select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.propertyId = :propertyId " +
            "and bmae.malAssetTypeId = :malAssetTypeId " +
            "and updated > :updated")
    List<BMAssetEntity> findPropertyAssets( final String propertyId, final String malAssetTypeId, final LocalDateTime updated );

    Slice<BMAssetEntity> findAllByTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusAndUpdatedIsAfter(
            final TransferringBMConnectionAssetStatus arg1, TransferringBMConnectionAssetStatus arg2, final LocalDateTime arg3, final Pageable page );

    Slice<BMAssetEntity> findAllByTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusAndUpdatedIsAfter(
            final TransferringBMConnectionAssetStatus arg1, final TransferringBMConnectionAssetStatus arg2, final TransferringBMConnectionAssetStatus arg3, final LocalDateTime arg4, final Pageable page );
}
