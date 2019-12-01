package MediaPoolMalBridge.service.BrandMaker.assets.getassetid;

import MediaPoolMalBridge.clients.BrandMaker.assetid.client.BMGetAssetIdFromHashClient;
import MediaPoolMalBridge.clients.BrandMaker.assetid.client.model.GetAssetIdFromMediaHashResponse;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetIdEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetIdRepository;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for getting asset id from hash achieved for asset id [%s]", assetEntity.getBmAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(assetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ERROR );
            assetRepository.save( assetEntity );
            return;
        }
        final GetAssetIdFromMediaHashResponse response = bmGetAssetIdFromHashClient.getAssetId( assetEntity );
        if (response.isStatus()) {
            onSuccess( assetEntity, response.getAssetId() );
        } else {
            reportErrorOnResponse(assetEntity.getBmAssetId(), response);
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_OBSERVED);
            assetRepository.save(assetEntity);
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
}
