package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.modified;

import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.MALGetModifiedPropertyPhotoClient;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoRequest;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetMap;
import MediaPoolMalBridge.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class MALCollectModifiedPropertiesPhotosService extends AbstractService {

    private final MALGetModifiedPropertyPhotoClient getModifiedPropertyPhotoClient;

    private final MALAssetMap malAssetMap;

    public MALCollectModifiedPropertiesPhotosService( final MALAssetMap malAssetMap,
                                                      final MALGetModifiedPropertyPhotoClient getModifiedPropertyPhotoClient)
    {
        this.malAssetMap = malAssetMap;
        this.getModifiedPropertyPhotoClient = getModifiedPropertyPhotoClient;
    }

    public void downloadModifiedPhotos( final String modifiedSince )
    {
        final MALGetModifiedPropertyPhotoRequest request = new MALGetModifiedPropertyPhotoRequest();
        request.setModifiedSince(modifiedSince);
        final RestResponse<MALGetModifiedPropertyPhotoResponse> response = getModifiedPropertyPhotoClient.download(request);
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getAssets() == null) {
            logger.error("Can not download list of modified asset since {}, with httpStatus {}, and responseBody {}",
                    modifiedSince,
                    response.getHttpStatus(),
                    GSON.toJson(response.getResponse()));
            return;
        }


        response.getResponse()
                .getAssets()
                .forEach(malAssetMap::updateWithMALGetModifiedPropertyPhoto);
    }
}
