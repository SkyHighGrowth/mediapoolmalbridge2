package com.brandmaker.mediapoolmalbridge.service.brandmaker.assets.getassetid;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetid.client.BMGetAssetIdFromHashClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetid.client.model.GetAssetIdFromMediaHashResponse;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bm.BMAssetIdEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.MALAssetOperation;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bm.BMAssetIdRepository;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMNonUniqueThreadService;
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
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_UPLOADED);
        assetRepository.save( assetEntity );
    }

    @Transactional
    public void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse) {
        if( isGateOpen(assetEntity, "getting asset id from hash", abstractBMResponse ) ) {
            reportErrorOnResponse(assetEntity.getBmAssetId(), abstractBMResponse);
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_ONBOARDED);
            assetRepository.save(assetEntity);
        }
    }
}
