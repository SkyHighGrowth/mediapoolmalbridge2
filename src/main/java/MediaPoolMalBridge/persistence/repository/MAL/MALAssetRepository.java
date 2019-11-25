package MediaPoolMalBridge.persistence.repository.MAL;

import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MALAssetRepository extends PagingAndSortingRepository<MALAssetEntity, Long> {

    public Slice<MALAssetEntity> findAllByTransferringAssetStatus(final TransferringAssetStatus transferringAssetStatus, final Pageable page );

    Optional<MALAssetEntity> findByAssetIdAndAssetType(final String assetId, final MALAssetType malAssetType);

    Slice<MALAssetEntity> findAllByTransferringMALConnectionAssetStatusOrTransferringAssetStatus(final TransferringMALConnectionAssetStatus arg1, final TransferringAssetStatus arg2, final Pageable page );

    /*@Query("select mae " +
            "from MALAssetEntity mae " +
            "where mae.transferringAssetStatus = :arg1 or " +
            "mae.transferringAssetStatus = :arg2" )*/
    Slice<MALAssetEntity> findAllByTransferringAssetStatusOrTransferringAssetStatus(final TransferringAssetStatus arg1, final TransferringAssetStatus arg2, final Pageable arg3  );
}
