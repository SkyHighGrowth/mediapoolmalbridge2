package MediaPoolMalBridge.clients.BrandMaker.assetupload.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import com.brandmaker.webservices.mediapool.UploadMediaResult;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.nio.file.Paths;

/**
 * Client that wraps MediapoolWebServicePort.uploadMediaAsStream
 */
@Component
public class BMUploadAssetClient extends BrandMakerSoapClient {

    public UploadStatus upload(final AssetEntity asset) {
        try {
            final String absoluteFilePath = getAppConfig().getTempDir() + asset.getFileNameOnDisc();
            fileExists( absoluteFilePath );
            final FileDataSource fileDataSource = new FileDataSource(absoluteFilePath);
            final DataHandler dataHandler = new DataHandler(fileDataSource);
            final UploadMediaResult result = getMediaPoolPort().uploadMediaAsStream(asset.getFileNameOnDisc(), dataHandler);
            return new UploadStatus(result, asset.getBmAssetId());
        } catch (final Exception e) {
            reportErrorOnException(asset.getBmAssetId(), e);
            return new UploadStatus(false, e.getMessage(), asset.getBmAssetId());
        }
    }

    private void fileExists( final String absoluteFilePath ) throws Exception {
        if( !Paths.get( absoluteFilePath ).toFile().isFile() ) {
            final String message = String.format( "File [%s] does not exists", absoluteFilePath );
            throw new Exception( message );
        }
    }
}
