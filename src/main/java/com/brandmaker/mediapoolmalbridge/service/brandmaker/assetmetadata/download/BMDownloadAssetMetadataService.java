package com.brandmaker.mediapoolmalbridge.service.brandmaker.assetmetadata.download;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetgetmediadetails.client.BMDownloadMediaDetailsClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for downloading Mediapool asset metadata
 */
@Service
public class BMDownloadAssetMetadataService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMDownloadMediaDetailsClient downloadMediaDetailsClient;

    public BMDownloadAssetMetadataService(final BMDownloadMediaDetailsClient downloadMediaDetailsClient )
    {
        this.downloadMediaDetailsClient = downloadMediaDetailsClient;
    }

    @Override
    protected void run(AssetEntity assetEntity) {
        final DownloadMediaDetailsResponse downloadMediaDetailsResponse = downloadMediaDetailsClient.download( assetEntity );
        if (!downloadMediaDetailsResponse.isStatus() ) {
            onFailure( assetEntity, downloadMediaDetailsResponse );
        } else {
            onSuccess( assetEntity, downloadMediaDetailsResponse.getGetMediaDetailsResult().getMediaHash() );
        }
    }

    @Transactional
    public void onSuccess( final AssetEntity assetEntity, final String bmMd5Hash )
    {
        assetEntity.setMalStatesRepetitions( 0 );
        assetEntity.setTransferringAssetStatus( TransferringAssetStatus.DONE );
        assetEntity.setBmMd5Hash( bmMd5Hash );
        assetRepository.save(assetEntity);
    }

    @Transactional
    public void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse ) {
        if( isGateOpen( assetEntity, "metadata downloading", abstractBMResponse ) ) {
            reportErrorOnResponse(assetEntity.getBmAssetId(), abstractBMResponse);
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.METADATA_UPLOADED );
            assetRepository.save( assetEntity );
        }
    }
}
