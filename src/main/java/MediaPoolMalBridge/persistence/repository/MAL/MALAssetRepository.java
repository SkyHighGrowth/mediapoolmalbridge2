package MediaPoolMalBridge.persistence.repository.MAL;

import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MALAssetRepository extends PagingAndSortingRepository<MALAssetEntity, Long> {

    public Slice<MALAssetEntity> findAllByTransferringAssetStatusAndUpdatedIsAfter(
            final TransferringAssetStatus transferringAssetStatus, final LocalDateTime updated, final Pageable page );

    Optional<MALAssetEntity> findByAssetIdAndAssetType(final String assetId, final MALAssetType malAssetType);

    Optional<MALAssetEntity> findByAssetIdAndAssetTypeAndUpdatedIsAfter(final String assetId, final MALAssetType malAssetType, final LocalDateTime updated);

    Slice<MALAssetEntity> findAllByTransferringMALConnectionAssetStatusOrTransferringAssetStatusAndUpdatedIsAfter(
            final TransferringMALConnectionAssetStatus arg1, final TransferringAssetStatus arg2, final LocalDateTime updated, final Pageable page );

    Slice<MALAssetEntity> findAllByTransferringMALConnectionAssetStatusOrTransferringMALConnectionAssetStatusAndUpdatedIsAfter(
            final TransferringMALConnectionAssetStatus transferringMALConnectionAssetStatus, final TransferringMALConnectionAssetStatus transferringAssetStatus, final LocalDateTime updated, final Pageable page );

    /*@Query("select mae " +
            "from MALAssetEntity mae " +
            "where mae.transferringAssetStatus = :arg1 or " +
            "mae.transferringAssetStatus = :arg2" )*/
    Slice<MALAssetEntity> findAllByTransferringAssetStatusOrTransferringAssetStatus(final TransferringAssetStatus arg1, final TransferringAssetStatus arg2, final Pageable arg3  );
}
