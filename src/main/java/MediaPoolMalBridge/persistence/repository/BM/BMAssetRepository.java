package MediaPoolMalBridge.persistence.repository.BM;

import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BMAssetRepository extends CrudRepository<BMAssetEntity, Long> {

    List<BMAssetEntity> findByTransferringBMConnectionAssetStatus(final TransferringBMConnectionAssetStatus transferringBMConnectionAssetStatus);

    @Query("select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.transferringBMConnectionAssetStatus = :arg1 or " +
            "bmae.transferringBMConnectionAssetStatus = :arg2")
    List<BMAssetEntity> findFileUploadedOrMetadataUploading( final TransferringBMConnectionAssetStatus arg1, final TransferringBMConnectionAssetStatus arg2);

    @Query("select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.transferringBMConnectionAssetStatus = :arg1 or " +
            "( bmae.transferringConnectionAssetStatus = :arg2 and " +
            "bmae.transferringMALConnectionAssetStatus = :arg3 )")
    List<BMAssetEntity> findFileCreatingOrMalCreatedAndDownloaded(final TransferringBMConnectionAssetStatus arg1, final TransferringAssetStatus arg2, final TransferringMALConnectionAssetStatus arg3);

    @Query("select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.transferringBMConnectionAssetStatus = :arg1 or " +
            "bmae.transferringConnectionAssetStatus = :arg2")
    List<BMAssetEntity> findFileDeletingOrMalDeleted( final TransferringBMConnectionAssetStatus arg1, final TransferringAssetStatus arg2 );

    @Query("select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.transferringBMConnectionAssetStatus = :arg1 or " +
            "( bmae.transferringConnectionAssetStatus = :arg2 and " +
            "bmae.transferringMALConnectionAssetStatus = :arg3 )")
    List<BMAssetEntity> findFileUploadingOrDownloadedAndMalUpdated( final TransferringBMConnectionAssetStatus arg1, final TransferringAssetStatus arg2, final TransferringMALConnectionAssetStatus arg3 );

    @Query("select bmae " +
            "from BMAssetEntity bmae " +
            "where bmae.transferringBMConnectionAssetStatus = :arg1 or " +
            "bmae.transferringBMConnectionAssetStatus = :arg2 or " +
            "bmae.transferringBMConnectionAssetStatus = :arg3 ")
    List<BMAssetEntity> findFilUploadedOrMetadataUploadingOrMetadataUploaded( final TransferringBMConnectionAssetStatus arg1, final TransferringBMConnectionAssetStatus arg2, final TransferringBMConnectionAssetStatus arg3);

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
}
