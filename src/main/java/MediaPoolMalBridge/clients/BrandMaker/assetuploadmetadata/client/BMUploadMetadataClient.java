package MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
import com.brandmaker.webservices.mediapool.UploadMetadataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BMUploadMetadataClient extends BrandMakerSoapClient {

    private final static Logger logger = LoggerFactory.getLogger(BMUploadMetadataClient.class);


    public BMUploadMetadataClient() {
    }

    public UploadMetadataStatus upload(final UploadMetadataArgument request, final boolean keepIfEmpty) {
        UploadMetadataStatus uploadMetadataStatus;
        try {
            final UploadMetadataResult result = getMediaPoolPort().uploadMetaData(request);
            uploadMetadataStatus = new UploadMetadataStatus(result);
        } catch (final Exception e) {
            logger.error("Can not upload metada request {}", request, e);
            uploadMetadataStatus = new UploadMetadataStatus(false, e.getMessage());
        }
        return uploadMetadataStatus;
    }
}
