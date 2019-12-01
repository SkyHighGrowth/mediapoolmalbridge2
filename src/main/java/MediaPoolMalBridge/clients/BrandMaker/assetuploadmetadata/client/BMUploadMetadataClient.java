package MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
import com.brandmaker.webservices.mediapool.UploadMetadataResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BMUploadMetadataClient extends BrandMakerSoapClient {

    public UploadMetadataStatus upload(final AssetEntity asset) {
        try {
            final UploadMetadataArgument uploadMetadataArgument = asset.getBmUploadMetadataArgument();
            uploadMetadataArgument.getFreeField5().remove( 0 );
            uploadMetadataArgument.getFreeField8().remove( 0 );
            if( StringUtils.isEmpty(asset.getBmAssetId() ) || asset.getBmAssetId().startsWith( "CREATING_" ) ) {
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
