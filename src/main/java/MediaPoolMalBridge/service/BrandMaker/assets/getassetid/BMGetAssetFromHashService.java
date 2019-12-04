package MediaPoolMalBridge.service.BrandMaker.assets.getassetid;

import MediaPoolMalBridge.clients.BrandMaker.assetid.client.BMGetAssetIdFromHashClient;
import MediaPoolMalBridge.clients.BrandMaker.assetid.client.model.GetAssetIdFromMediaHashResponse;
import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetIdEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetIdRepository;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service which get asset id from Mediapool server for specific asset
 */
@Service
public class BMGetAssetFromHashService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMGetAssetIdFromHashClient bmGetAssetIdFromHashClient;

    private final BMAssetIdRepository bmAssetIdRepository;

    public BMGetAssetFromHashService(final BMGetAssetIdFromHashClient bmGetAssetIdFromHashClient,
                                     final BMAssetIdRepository bmAssetIdRepository) {
        this.bmGetAssetIdFromHashClient = bmGetAssetIdFromHashClient;
        this.bmAssetIdRepository = bmAssetIdRepository;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        if( !isGateOpen(assetEntity, "getting asset id from hash") ) {
            return;
        }
        final GetAssetIdFromMediaHashResponse response = bmGetAssetIdFromHashClient.getAssetId( assetEntity );
        if (response.isStatus()) {
            onSuccess( assetEntity, response.getAssetId() );
        } else {
            onFailure(assetEntity, response);
        }
    }

    @Transactional
    public void onSuccess( final AssetEntity assetEntity, final String bmAssetId ) {
        final BMAssetIdEntity bmAssetIdEntity = assetEntity.getBmAssetIdEntity();
        bmAssetIdEntity.setBmAssetId( bmAssetId );
        bmAssetIdRepository.save( bmAssetIdEntity );
        assetEntity.setMalAssetOperation( MALAssetOperation.MAL_MODIFIED );
        assetEntity.setMalStatesRepetitions( 0 );
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_DOWNLOADED);
        assetRepository.save( assetEntity );
    }

    @Transactional
    protected void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse) {
        reportErrorOnResponse(assetEntity.getBmAssetId(), abstractBMResponse);
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_OBSERVED);
        assetRepository.save(assetEntity);
    }
}
