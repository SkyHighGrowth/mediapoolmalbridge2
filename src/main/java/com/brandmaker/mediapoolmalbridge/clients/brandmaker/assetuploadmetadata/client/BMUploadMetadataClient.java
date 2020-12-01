package com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetuploadmetadata.client;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.BrandMakerSoapClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetJsonedValuesEntity;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.AssetJsonedValuesRepository;
import com.brandmaker.webservices.mediapool.UploadMetadataArgumentVersion2;
import com.brandmaker.webservices.mediapool.UploadMetadataResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Client that wraps call to MediapoolWebServicePort.uploadMetaData
 */
@Component
public class BMUploadMetadataClient extends BrandMakerSoapClient {

    final AssetJsonedValuesRepository assetJsonedValuesRepository;

    public BMUploadMetadataClient( final AssetJsonedValuesRepository assetJsonedValuesRepository ) {
        this.assetJsonedValuesRepository = assetJsonedValuesRepository;
    }

    public UploadMetadataStatus upload(final AssetEntity asset) {
        try {
            final UploadMetadataArgumentVersion2 uploadMetadataArgument = getUploadMetadataArgument( asset );
            if( StringUtils.isEmpty(asset.getBmAssetId() ) ||
                asset.getBmAssetId().startsWith( "CREATING_" ) ||
                uploadMetadataArgument == null ) {
                return new UploadMetadataStatus( false, "Asset id " + asset.getBmAssetId() + " is not valid" );
            }
            uploadMetadataArgument.setMediaGuid( asset.getBmAssetId() );
            uploadMetadataArgument.setMediaNumber( "M-" + asset.getBmAssetId() );
            final UploadMetadataResult result = getMediaPoolPort().uploadMetaData(uploadMetadataArgument);
            return new UploadMetadataStatus(result);
        } catch (final Exception e) {
            reportErrorOnException(asset.getBmAssetId(), e);
            return new UploadMetadataStatus(false, e.getMessage());
        }
    }

    private UploadMetadataArgumentVersion2 getUploadMetadataArgument(final AssetEntity assetEntity ) {
        AssetJsonedValuesEntity jsonedValuesEntity = assetEntity.getAssetJsonedValuesEntity();
        if (jsonedValuesEntity != null) {
            final AssetJsonedValuesEntity assetJsonedValuesEntity = assetJsonedValuesRepository.findAssetJsonedValuesEntitiesById(jsonedValuesEntity.getId());
            if (assetJsonedValuesEntity != null && assetJsonedValuesEntity.getBmUploadMetadataArgumentJson() != null) {
                return GSON.fromJson(assetJsonedValuesEntity.getBmUploadMetadataArgumentJson(), UploadMetadataArgumentVersion2.class);
            }
        }
        return null;
    }
}
