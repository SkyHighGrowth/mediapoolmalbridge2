package MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
import com.brandmaker.webservices.mediapool.UploadMetadataResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BMUploadMetadataClient extends BrandMakerSoapClient {

    public UploadMetadataStatus upload(final BMAssetEntity bmAsset) {
        try {
            final UploadMetadataArgument uploadMetadataArgument = bmAsset.getUploadMetadataArgument();
            if( StringUtils.isEmpty(bmAsset.getAssetId() ) || bmAsset.getAssetId().startsWith( "CREATING_" ) ) {
                return new UploadMetadataStatus( false, "Asset id " + bmAsset.getAssetId() + " is not valid" );
            }
            uploadMetadataArgument.setMediaGuid( bmAsset.getAssetId() );
            uploadMetadataArgument.setMediaNumber( "M-" + bmAsset.getAssetId() );
            final UploadMetadataResult result = getMediaPoolPort().uploadMetaData(bmAsset.getUploadMetadataArgument());
            return new UploadMetadataStatus(result);
        } catch (final Exception e) {
            reportErrorOnException(bmAsset.getAssetId(), e);
            return new UploadMetadataStatus(false, e.getMessage());
        }
    }
}
