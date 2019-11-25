package MediaPoolMalBridge.service.BrandMaker.assetmetadata.download;

import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.BMDownloadMediaDetailsClient;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueService;
import org.springframework.stereotype.Service;

@Service
public class BMDownloadAssetMetadataService extends AbstractBMNonUniqueService<BMAssetEntity> {

    private final BMDownloadMediaDetailsClient downloadMediaDetailsClient;

    public BMDownloadAssetMetadataService(final BMDownloadMediaDetailsClient downloadMediaDetailsClient )
    {
        this.downloadMediaDetailsClient = downloadMediaDetailsClient;
    }

    @Override
    protected void run(BMAssetEntity bmAssetEntity) {
        bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_DOWNLOADING);
        final DownloadMediaDetailsResponse downloadMediaDetailsResponse = downloadMediaDetailsClient.download( bmAssetEntity );
        if (!downloadMediaDetailsResponse.isStatus()) {
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_DOWNLOADING);
            reportErrorOnResponse(bmAssetEntity.getAssetId(), downloadMediaDetailsResponse);
            bmAssetRepository.save(bmAssetEntity);
        } else {
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_DOWNLOADED);
            bmAssetEntity.setBmHash( downloadMediaDetailsResponse.getGetMediaDetailsResult().getMediaHash() );
        }
        bmAssetRepository.save(bmAssetEntity);
        synchronized ( this ) {
            notify();
        }
    }
}
