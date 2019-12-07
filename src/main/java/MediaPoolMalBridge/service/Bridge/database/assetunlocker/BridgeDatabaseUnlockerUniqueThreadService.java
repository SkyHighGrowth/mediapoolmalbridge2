package MediaPoolMalBridge.service.Bridge.database.assetunlocker;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.Bridge.AbstractBridgeUniqueThreadService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BridgeDatabaseUnlockerUniqueThreadService extends AbstractBridgeUniqueThreadService {

    @Override
    protected void run() {

        for( int page = 0; true; ++page ) {
            final List<AssetEntity> assetEntities = assetRepository.findAllByUpdatedIsAfter(
                    getMidnightBridgeLookInThePast(), PageRequest.of(page, appConfig.getDatabasePageSize(), Sort.by( Sort.Direction.ASC, "updated" ) ) );
            if( assetEntities.isEmpty() || page > 50000 ) {
                break;
            }
            onBoardPage( assetEntities );
        }
    }

    @Transactional
    protected void onBoardPage( final List<AssetEntity> assetEntities ) {
        assetEntities.forEach(assetEntity -> {
            if( !TransferringAssetStatus.ASSET_OBSERVED.equals( assetEntity.getTransferringAssetStatus() ) ) {
                return;
            }
            final long notDone = assetRepository.countByMalAssetIdAndAssetTypeAndTransferringAssetStatusNotAndTransferringAssetStatusNotAndUpdatedIsBeforeAndUpdatedIsAfter(
                    assetEntity.getMalAssetId(), assetEntity.getAssetType(), TransferringAssetStatus.DONE, TransferringAssetStatus.ERROR, assetEntity.getUpdated(), getMidnightBridgeLookInThePast());
            if (notDone == 0) {
                assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_ONBOARDED);
            }
            assetRepository.save( assetEntity );
        });
    }
}
