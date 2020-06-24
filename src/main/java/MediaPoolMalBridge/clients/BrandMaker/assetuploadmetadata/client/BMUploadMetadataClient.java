package MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetJsonedValuesEntity;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetJsonedValuesRepository;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
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
            final UploadMetadataArgument uploadMetadataArgument = getUploadMetadataArgument( asset );
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

    private UploadMetadataArgument getUploadMetadataArgument(final AssetEntity assetEntity ) {
        final AssetJsonedValuesEntity assetJsonedValuesEntity = assetJsonedValuesRepository.findByAssetEntity( assetEntity );
        if (assetJsonedValuesEntity != null && assetJsonedValuesEntity.getBmUploadMetadataArgumentJson() != null) {
            return GSON.fromJson(assetJsonedValuesEntity.getBmUploadMetadataArgumentJson(), UploadMetadataArgument.class);
        }
        return null;
    }

    private String toZeroAndOne( final String v )
    {
        if( "true".equals( v ) ) {
            return "1";
        }
        if( "false".equals( v) ) {
            return "0";
        }
        return "0";
    }
}
