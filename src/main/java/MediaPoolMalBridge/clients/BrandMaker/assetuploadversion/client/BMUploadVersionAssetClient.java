package MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import com.brandmaker.webservices.mediapool.UploadMediaVersionResult;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

/**
 * Client that wraps calls to MediapoolWebServicePort.uploadMediaVersionAsStream
 */
@Component
public class BMUploadVersionAssetClient extends BrandMakerSoapClient {

    public UploadStatus upload(final AssetEntity asset) {
        try {
            final String absoluteFilePath = getAppConfig().getTempDir() + asset.getFileNameOnDisc();
            final FileDataSource fileDataSource = new FileDataSource(absoluteFilePath);
            final DataHandler dataHandler = new DataHandler(fileDataSource);
            final UploadMediaVersionResult result = getMediaPoolPort().uploadMediaVersionAsStream(asset.getBmAssetId(), "uploaded from MediaPoolSyncTool", asset.getFileNameOnDisc(), dataHandler);
            return new UploadStatus(result, asset.getBmAssetId());
        } catch (final Exception e) {
            reportErrorOnException(asset.getBmAssetId(), e);
            return new UploadStatus(false, e.getMessage(), asset.getBmAssetId());
        }
    }
}
