package MediaPoolMalBridge.clients.BrandMaker.assetupload.client;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.clients.BrandMaker.model.BMAsset;
import MediaPoolMalBridge.model.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.MALIdToBMIdEntity;
import com.brandmaker.webservices.mediapool.UploadMediaResult;
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

    public UploadStatus upload(final BMAsset bmAsset) {

        UploadStatus uploadStatus;
        try {
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.CREATING);
            final String absoluteFilePath = appConfig.getTempDir() + bmAsset.getFileName();
            final FileDataSource fileDataSource = new FileDataSource(absoluteFilePath);
            final DataHandler dataHandler = new DataHandler(fileDataSource);
            final UploadMediaResult result = getMediaPoolPort().uploadMediaAsStream( bmAsset.getFileName(), dataHandler);
            if (result.isSuccess()) {
                final MALIdToBMIdEntity malIdToBMIdEntity = new MALIdToBMIdEntity();
                malIdToBMIdEntity.setBmId( result.getMediaGuid() );
                malIdToBMIdEntity.setSalId( bmAsset.getUploadMetadataArgument().getFreeField1().get( 0 ).getDescription() );
                bmAsset.setAssetId( result.getMediaGuid() );
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.CREATED);
            }
            else
            {
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.CREATING);
            }
            uploadStatus = new UploadStatus(result, bmAsset);
        } catch (final Exception e) {
            logger.error("Error uploading asset {}", bmAsset, e);
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.CREATING);
            uploadStatus = new UploadStatus(false, e.getMessage(), bmAsset);
        }
        return uploadStatus;
    }
}
