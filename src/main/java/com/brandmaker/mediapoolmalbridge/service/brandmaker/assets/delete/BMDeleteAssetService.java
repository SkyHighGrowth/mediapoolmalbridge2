package com.brandmaker.mediapoolmalbridge.service.brandmaker.assets.delete;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetdelete.client.BMDeleteAssetClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetdelete.client.model.DeleteMediaResponse;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMNonUniqueThreadService;
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
        final DeleteMediaResponse deleteMediaResponse = bmDeleteAssetClient.delete( assetEntity );
        if (deleteMediaResponse.isStatus()) {
            onSucces( assetEntity );
        } else {
            onFailure( assetEntity, deleteMediaResponse );
        }
        assetRepository.save(assetEntity);
    }

    @Transactional
    public void onSucces( final AssetEntity assetEntity ) {
        assetEntity.setMalStatesRepetitions( 0 );
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.DONE);
        assetRepository.save( assetEntity );
    }

    @Transactional
    public void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse) {
        if( isGateOpen( assetEntity, "delete asset", abstractBMResponse ) ) {
            reportErrorOnResponse(assetEntity.getBmAssetId(), abstractBMResponse);
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_ONBOARDED);
            assetRepository.save( assetEntity );
        }
    }
}
