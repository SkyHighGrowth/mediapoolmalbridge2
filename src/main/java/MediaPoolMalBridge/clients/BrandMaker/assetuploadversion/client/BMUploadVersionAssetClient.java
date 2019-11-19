package MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import com.brandmaker.webservices.mediapool.UploadMediaVersionResult;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

@Component
public class BMUploadVersionAssetClient extends BrandMakerSoapClient {

    public UploadStatus upload(final BMAssetEntity bmAsset) {

        UploadStatus uploadStatus;
        try {
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_UPLOADING);
            final String absoluteFilePath = getAppConfig().getTempDir() + bmAsset.getFileName();
            final FileDataSource fileDataSource = new FileDataSource(absoluteFilePath);
            final DataHandler dataHandler = new DataHandler(fileDataSource);
            final UploadMediaVersionResult result = getMediaPoolPort().uploadMediaVersionAsStream(bmAsset.getAssetId(), "uploaded from MediaPoolSyncTool", bmAsset.getFileName(), dataHandler);
            uploadStatus = new UploadStatus(result, bmAsset.getAssetId());
            if (result.isSuccess()) {
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_UPLOADED);
            } else {
                reportErrorOnResponse(bmAsset.getAssetId(), uploadStatus);
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_UPLOADING);
            }

        } catch (final Exception e) {
            reportErrorOnException(bmAsset.getAssetId(), e);
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_UPLOADING);
            uploadStatus = new UploadStatus(false, e.getMessage(), bmAsset.getAssetId());
        }
        bmAssetRepository.save(bmAsset);
        return uploadStatus;
    }
}
