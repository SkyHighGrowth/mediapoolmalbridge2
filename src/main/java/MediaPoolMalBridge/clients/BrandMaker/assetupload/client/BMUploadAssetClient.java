package MediaPoolMalBridge.clients.BrandMaker.assetupload.client;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetupload.client.model.UploadStatus;
import MediaPoolMalBridge.model.asset.Asset;
import MediaPoolMalBridge.model.asset.AssetStatus;
import com.brandmaker.webservices.mediapool.UploadMediaVersionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

@Component
public class BMUploadAssetClient extends BrandMakerSoapClient {

    private final static Logger logger = LoggerFactory.getLogger(BMUploadAssetClient.class);

    private final AppConfig appConfig;

    public BMUploadAssetClient(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public UploadStatus upload(final Asset asset) {

        UploadStatus uploadStatus;
        asset.setAssetStatus(AssetStatus.UPLOADING);
        try {
            final String absoluteFilePath = appConfig.getTempDir() + asset.getFileName();
            final FileDataSource fileDataSource = new FileDataSource(absoluteFilePath);
            final DataHandler dataHandler = new DataHandler(fileDataSource);
            final UploadMediaVersionResult result = getMediaPoolPort().uploadMediaVersionAsStream(asset.getAssetId(), "uploaded from MediaPoolSyncTool", asset.getFileName(), dataHandler);
            if (result.isSuccess()) {
                asset.setAssetStatus(AssetStatus.UPLOADED);
            }
            uploadStatus = new UploadStatus(result, asset.getFileName());
        } catch (final Exception e) {
            logger.error("Error uploading asset {}", asset, e);
            uploadStatus = new UploadStatus(false, e.getMessage(), asset.getFileName());
        }
        return uploadStatus;
    }
}
