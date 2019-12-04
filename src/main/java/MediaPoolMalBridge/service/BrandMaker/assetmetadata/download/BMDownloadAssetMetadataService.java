package MediaPoolMalBridge.service.BrandMaker.assetmetadata.download;

import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.BMDownloadMediaDetailsClient;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
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
        if( !isGateOpen( assetEntity, "metadata downloading" ) ) {
            return;
        }
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
    public void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse )
    {
        assetEntity.setTransferringAssetStatus( TransferringAssetStatus.METADATA_UPLOADED );
        reportErrorOnResponse(assetEntity.getBmAssetId(), abstractBMResponse);
        assetRepository.save( assetEntity );
    }
}
