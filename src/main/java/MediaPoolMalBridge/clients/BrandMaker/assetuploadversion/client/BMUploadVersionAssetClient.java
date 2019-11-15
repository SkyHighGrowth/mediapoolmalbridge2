package MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.clients.BrandMaker.model.BMAsset;
import MediaPoolMalBridge.model.asset.TransferringBMConnectionAssetStatus;
import com.brandmaker.webservices.mediapool.UploadMediaVersionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

@Component
public class BMUploadVersionAssetClient extends BrandMakerSoapClient {

    private final static Logger logger = LoggerFactory.getLogger(BMUploadVersionAssetClient.class);

    private final AppConfig appConfig;

    public BMUploadVersionAssetClient(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public UploadStatus upload(final BMAsset bmAsset) {

        UploadStatus uploadStatus;
        try {
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.UPLOADING);
            final String absoluteFilePath = appConfig.getTempDir() + bmAsset.getFileName();
            final FileDataSource fileDataSource = new FileDataSource(absoluteFilePath);
            final DataHandler dataHandler = new DataHandler(fileDataSource);
            final UploadMediaVersionResult result = getMediaPoolPort().uploadMediaVersionAsStream(bmAsset.getAssetId(), "uploaded from MediaPoolSyncTool", bmAsset.getFileName(), dataHandler);
            if (result.isSuccess()) {
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.UPLOADED);
            }
            else
            {
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.UPLOADING);
            }
            uploadStatus = new UploadStatus(result, bmAsset);
        } catch (final Exception e) {
            logger.error("Error uploading asset {}", bmAsset, e);
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.UPLOADING);
            uploadStatus = new UploadStatus(false, e.getMessage(), bmAsset);
        }
        return uploadStatus;
    }
}
