package MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.BMUploadMetadataClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service which uploads asset metadata
 */
@Service
public class BMUploadAssetMetadataService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMUploadMetadataClient bmUploadMetadataClient;

    public BMUploadAssetMetadataService(final BMUploadMetadataClient bmUploadMetadataClient ) {
        this.bmUploadMetadataClient = bmUploadMetadataClient;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        final UploadMetadataStatus uploadMetadataStatus = bmUploadMetadataClient.upload( assetEntity );
        if (uploadMetadataStatus.isStatus()) {
            onSuccess( assetEntity );
        } else {
            onFailure( assetEntity, uploadMetadataStatus );
        }
    }

    @Transactional
    public void onSuccess( final AssetEntity assetEntity ) {
        assetEntity.setMalStatesRepetitions( 0 );
        assetEntity.setTransferringAssetStatus( TransferringAssetStatus.METADATA_UPLOADED );
        assetRepository.save(assetEntity);
    }

    @Transactional
    public void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse) {
        if( isGateOpen( assetEntity, "metadata uploading", abstractBMResponse ) ) {
            reportErrorOnResponse(assetEntity.getBmAssetId(), abstractBMResponse);
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.FILE_UPLOADED );
            assetRepository.save(assetEntity);
        }
    }
}
