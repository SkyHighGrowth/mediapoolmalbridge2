package MediaPoolMalBridge.clients.BrandMaker.assetupload.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import com.brandmaker.webservices.mediapool.UploadMediaResult;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

@Component
public class BMUploadAssetClient extends BrandMakerSoapClient {

    public UploadStatus upload(final BMAssetEntity bmAsset) {

        UploadStatus uploadStatus;
        try {
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_CREATING);
            final String absoluteFilePath = getAppConfig().getTempDir() + bmAsset.getFileName();
            final FileDataSource fileDataSource = new FileDataSource(absoluteFilePath);
            final DataHandler dataHandler = new DataHandler(fileDataSource);
            final UploadMediaResult result = getMediaPoolPort().uploadMediaAsStream(bmAsset.getFileName(), dataHandler);
            uploadStatus = new UploadStatus(result, bmAsset.getAssetId());
            if (result.isSuccess()) {
                bmAsset.setAssetId(result.getMediaGuid());
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_CREATED);
            } else {
                reportErrorOnResponse(bmAsset.getAssetId(), uploadStatus);
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_CREATING);
            }
        } catch (final Exception e) {
            reportErrorOnException(bmAsset.getAssetId(), e);
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_CREATING);
            uploadStatus = new UploadStatus(false, e.getMessage(), bmAsset.getAssetId());
        }
        bmAssetRepository.save(bmAsset);
        return uploadStatus;
    }
}
