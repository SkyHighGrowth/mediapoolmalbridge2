package MediaPoolMalBridge.service.BrandMaker.assets.delete;

import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.BMDeleteAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service which deletes asset from Mediapool service
 */
@Service
public class BMDeleteAssetService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMDeleteAssetClient bmDeleteAssetClient;

    public BMDeleteAssetService(final BMDeleteAssetClient bmDeleteAssetClient) {
        this.bmDeleteAssetClient = bmDeleteAssetClient;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        if( !isGateOpen( assetEntity, "delete asset" ) ) {
            return;
        }
        final DeleteMediaResponse deleteMediaResponse = bmDeleteAssetClient.delete( assetEntity );
        if (deleteMediaResponse.isStatus()) {
            onSucces( assetEntity );
        } else {
            onFailure( assetEntity, deleteMediaResponse );
        }
        assetRepository.save(assetEntity);
    }

    @Transactional
    protected void onSucces( final AssetEntity assetEntity ) {
        assetEntity.setMalStatesRepetitions( 0 );
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.DONE);
        assetRepository.save( assetEntity );
    }

    @Transactional
    protected void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse) {
        reportErrorOnResponse(assetEntity.getBmAssetId(), abstractBMResponse);
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_ONBOARDED);
        assetRepository.save( assetEntity );
    }
}
