package MediaPoolMalBridge.clients.BrandMaker.assetupload.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import com.brandmaker.webservices.mediapool.UploadMediaResult;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

@Component
public class BMUploadAssetClient extends BrandMakerSoapClient {

    public UploadStatus upload(final BMAssetEntity bmAsset) {
        try {
            final String absoluteFilePath = getAppConfig().getTempDir() + bmAsset.getFileName();
            final FileDataSource fileDataSource = new FileDataSource(absoluteFilePath);
            final DataHandler dataHandler = new DataHandler(fileDataSource);
            final UploadMediaResult result = getMediaPoolPort().uploadMediaAsStream(bmAsset.getFileName(), dataHandler);
            return new UploadStatus(result, bmAsset.getAssetId());
        } catch (final Exception e) {
            reportErrorOnException(bmAsset.getAssetId(), e);
            return new UploadStatus(false, e.getMessage(), bmAsset.getAssetId());
        }
    }
}
