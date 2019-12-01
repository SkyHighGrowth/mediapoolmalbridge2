package MediaPoolMalBridge.service.BrandMaker.assets.delete;

import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.BMDeleteAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMDeleteAssetService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMDeleteAssetClient bmDeleteAssetClient;

    public BMDeleteAssetService(final BMDeleteAssetClient bmDeleteAssetClient) {
        this.bmDeleteAssetClient = bmDeleteAssetClient;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for delete asset achieved for asset id [%s]", assetEntity.getBmAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(assetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ERROR );
            assetRepository.save( assetEntity );
            return;
        }
        final DeleteMediaResponse deleteMediaResponse = bmDeleteAssetClient.delete( assetEntity );
        if (deleteMediaResponse.isStatus()) {
            assetEntity.setMalStatesRepetitions( 0 );
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.DONE);
        } else {
            reportErrorOnResponse(assetEntity.getBmAssetId(), deleteMediaResponse);
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_OBSERVED);
        }
        assetRepository.save(assetEntity);
    }
}
