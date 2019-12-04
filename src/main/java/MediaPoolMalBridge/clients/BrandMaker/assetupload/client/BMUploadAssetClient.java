package MediaPoolMalBridge.clients.BrandMaker.assetupload.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import com.brandmaker.webservices.mediapool.UploadMediaResult;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

/**
 * Client that wraps MediapoolWebServicePort.uploadMediaAsStream
 */
@Component
public class BMUploadAssetClient extends BrandMakerSoapClient {

    public UploadStatus upload(final AssetEntity asset) {
        try {
            final String absoluteFilePath = getAppConfig().getTempDir() + asset.getFileNameOnDisc();
            final FileDataSource fileDataSource = new FileDataSource(absoluteFilePath);
            final DataHandler dataHandler = new DataHandler(fileDataSource);
            final UploadMediaResult result = getMediaPoolPort().uploadMediaAsStream(asset.getFileNameOnDisc(), dataHandler);
            return new UploadStatus(result, asset.getBmAssetId());
        } catch (final Exception e) {
            reportErrorOnException(asset.getBmAssetId(), e);
            return new UploadStatus(false, e.getMessage(), asset.getBmAssetId());
        }
    }
}
