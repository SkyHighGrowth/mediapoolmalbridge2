package MediaPoolMalBridge.service.MAL.assets.deleted;

import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.MALGetUnavailableAssetsClient;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAsset;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsRequest;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsResponse;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetJsonedValuesEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service that collects unavailable assets from MAL server and stores them to {@link AssetEntity}
 */
@Service
public class MALCollectDeletedAssetsUniqueThreadSinceService extends AbstractMALUniqueThreadService {

    private final MALGetUnavailableAssetsClient getUnavailableAssetsClient;

    /**
     * Point in time for which we are collecting
     */
    private String since;

    public MALCollectDeletedAssetsUniqueThreadSinceService(final MALGetUnavailableAssetsClient getUnavailableAssetsClient) {
        this.getUnavailableAssetsClient = getUnavailableAssetsClient;
    }

    @Override
    protected void run() {
        final MALGetUnavailableAssetsRequest request = new MALGetUnavailableAssetsRequest();
        request.setUnavailableSince(since);

        final RestResponse<MALGetUnavailableAssetsResponse> response = getUnavailableAssetsClient.download(request);

        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getAssets() == null) {
            final String message = String.format("Can not download list of unavailable assets since [%s] with response [%s]",
                    since,
                    GSON.toJson(response.getResponse()));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        transformToAssets( response.getResponse().getAssets() );
    }

    @Transactional
    protected void transformToAssets( final List<MALGetUnavailableAsset> assets )
    {
        assets.forEach(malGetUnavailableAsset -> {
                    addAssetToDelete(malGetUnavailableAsset, MALAssetType.FILE);
                    addAssetToDelete(malGetUnavailableAsset, MALAssetType.JPG_LOGO);
                    addAssetToDelete(malGetUnavailableAsset, MALAssetType.PNG_LOGO);
                });
    }

    private void addAssetToDelete(final MALGetUnavailableAsset malGetUnavailableAsset, final MALAssetType assetType) {
        final List<AssetEntity> malAssetEntities = assetRepository.findAllByMalAssetIdAndAssetType(malGetUnavailableAsset.getAssetId(), assetType);
        if (malAssetEntities == null || malAssetEntities.isEmpty()) {
            return;
        }
        for( final AssetEntity assetEntity : malAssetEntities ) {
            if( MALAssetOperation.MAL_DELETED.equals( assetEntity.getMalAssetOperation() ) ) {
                return;
            }
        }
        final AssetJsonedValuesEntity assetJsonedValuesEntity = new AssetJsonedValuesEntity();
        assetJsonedValuesEntity.setMalGetUnavailableAssetJson( GSON.toJson( malGetUnavailableAsset ) );
        final AssetEntity assetEntity = new AssetEntity();
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_OBSERVED);
        assetEntity.setMalAssetOperation( MALAssetOperation.MAL_DELETED );
        assetEntity.setAssetType( assetType );
        assetEntity.setMalAssetId( malGetUnavailableAsset.getAssetId() );
        assetEntity.setPropertyId( malGetUnavailableAsset.getPropertyId() );
        assetEntity.setBmAssetIdEntity( malAssetEntities.get( 0 ).getBmAssetIdEntity() );
        assetEntity.setAssetJsonedValuesEntity( assetJsonedValuesEntity );
        assetRepository.save(assetEntity);
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }
}
