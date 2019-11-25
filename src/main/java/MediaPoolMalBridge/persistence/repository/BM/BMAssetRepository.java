package MediaPoolMalBridge.persistence.repository.BM;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional ( readOnly = true )
public interface BMAssetRepository extends PagingAndSortingRepository<BMAssetEntity, Long> {

    Slice<BMAssetEntity> findAllByTransferringBMConnectionAssetStatusOrTransferringConnectionAssetStatusAndTransferringMALConnectionAssetStatus(
            final TransferringBMConnectionAssetStatus arg1, final TransferringAssetStatus arg2, final TransferringMALConnectionAssetStatus arg3, final Pageable pageable );

    @Query( "select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.transferringBMConnectionAssetStatus = :arg1 or " +
            "( bmae.transferringBMConnectionAssetStatus = :arg2 and " +
            "bmae.transferringConnectionAssetStatus = :arg3 and " +
            "bmae.transferringMALConnectionAssetStatus = :arg4 )")
    Slice<BMAssetEntity> findTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusAndTransferringConnectionAssetStatusAndTransferringMALConnectionAssetStatus(
            final TransferringBMConnectionAssetStatus arg1, final TransferringBMConnectionAssetStatus arg2, final TransferringAssetStatus arg3, final TransferringMALConnectionAssetStatus arg4, final Pageable pageable );

    Slice<BMAssetEntity> findAllByTransferringBMConnectionAssetStatusOrTransferringConnectionAssetStatus( final TransferringBMConnectionAssetStatus arg1, final TransferringAssetStatus arg2, final Pageable page );

    @Query("select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.transferringBMConnectionAssetStatus = :arg1 or " +
            "bmae.transferringBMConnectionAssetStatus = :arg2 or " +
            "bmae.transferringBMConnectionAssetStatus = :arg3 ")
    List<BMAssetEntity> findFilUploadedOrMetadataUploadingOrMetadataUploaded( final TransferringBMConnectionAssetStatus arg1, final TransferringBMConnectionAssetStatus arg2, final TransferringBMConnectionAssetStatus arg3);


    //strange but original program takes into account only first asset
    @Query( "select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.propertyId = :propertyId " +
            "and bmae.malAssetTypeId = :malAssetTypeId " +
            "and bmae.malColorId like ':colorId%' " +
            "and bmae.transferringBMConnectionAssetStatus = :transferringBMConnectionAssetStatus" )
    List<BMAssetEntity> findAssetDetails( final String propertyId, final String malAssetTypeId, final String colorId, final TransferringBMConnectionAssetStatus transferringBMConnectionAssetStatus );

    @Query( "select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.propertyId = :propertyId " +
            "and bmae.malAssetTypeId = :malAssetTypeId" )
    List<BMAssetEntity> findPropertyAssets( final String propertyId, final String malAssetTypeId );

    @Query( "select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.transferringBMConnectionAssetStatus = :arg1 or " +
            "bmae.transferringBMConnectionAssetStatus = :arg2 " )
    List<BMAssetEntity> findMetadataUploadedOrMetadataDownloading( final TransferringBMConnectionAssetStatus arg1, TransferringBMConnectionAssetStatus arg2 );

    Slice<BMAssetEntity> findAllByTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatus(final TransferringBMConnectionAssetStatus arg1, TransferringBMConnectionAssetStatus arg2, final Pageable page );

    Slice<BMAssetEntity> findAllByTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatus(
            final TransferringBMConnectionAssetStatus arg1, final TransferringBMConnectionAssetStatus arg2, final TransferringBMConnectionAssetStatus arg3, final Pageable page );
}
