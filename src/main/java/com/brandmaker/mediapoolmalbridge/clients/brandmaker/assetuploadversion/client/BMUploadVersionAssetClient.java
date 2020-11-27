package com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetuploadversion.client;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.BrandMakerSoapClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetuploadversion.client.model.UploadStatus;
import com.brandmaker.mediapoolmalbridge.constants.Constants;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.webservices.mediapool.UploadMediaVersionResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.nio.file.Paths;

/**
 * Client that wraps calls to MediapoolWebServicePort.uploadMediaVersionAsStream
 */
@Component
public class BMUploadVersionAssetClient extends BrandMakerSoapClient {

    public UploadStatus upload(final AssetEntity asset) {
        try {
            final String absoluteFilePath = getAppConfig().getTempDir() + asset.getFileNameOnDisc();
            fileExists( absoluteFilePath );
            final FileDataSource fileDataSource = new FileDataSource(absoluteFilePath);
            final DataHandler dataHandler = new DataHandler(fileDataSource);
            final UploadMediaVersionResult result = getMediaPoolPort().uploadMediaVersionAsStream(asset.getBmAssetId(), "uploaded from MediaPoolSyncTool", StringUtils.substringAfter( asset.getFileNameOnDisc(), Constants.SPLITTER ), dataHandler);
            return new UploadStatus(result, asset.getBmAssetId());
        } catch (final Exception e) {
            reportErrorOnException(asset.getBmAssetId(), e);
            return new UploadStatus(false, e.getMessage(), asset.getBmAssetId());
        }
    }

    private void fileExists( final String absoluteFilePath ) {
        if( !Paths.get( absoluteFilePath ).toFile().isFile() ) {
            final String message = String.format( "File [%s] does not exists", absoluteFilePath );
            throw new IllegalArgumentException( message );
        }
    }
}
