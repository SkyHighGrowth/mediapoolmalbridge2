package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.modified;

import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.MALGetModifiedPropertyPhotoClient;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoRequest;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class MALCollectModifiedPropertiesPhotosService extends AbstractMALService {

    private final MALGetModifiedPropertyPhotoClient getModifiedPropertyPhotoClient;

    public MALCollectModifiedPropertiesPhotosService(final MALGetModifiedPropertyPhotoClient getModifiedPropertyPhotoClient) {
        this.getModifiedPropertyPhotoClient = getModifiedPropertyPhotoClient;
    }

    public void downloadModifiedPhotos(final String modifiedSince) {
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
                .forEach(malModifiedPropertyPhotoAsset -> {
                    MALAssetEntity malAssetEntity;
                    if (StringUtils.isNotBlank(malModifiedPropertyPhotoAsset.getJpgDownloadUrl())) {
                        malAssetEntity = MALAssetEntity.fromMALGetModifiedPropertyPhotos(malModifiedPropertyPhotoAsset, MALAssetType.PHOTO_JPG_LOGO);
                    } else if (StringUtils.isNotBlank(malModifiedPropertyPhotoAsset.getMediumDownloadUrl())) {
                        malAssetEntity = MALAssetEntity.fromMALGetModifiedPropertyPhotos(malModifiedPropertyPhotoAsset, MALAssetType.PHOTO_MEDIUM);
                    } else if (StringUtils.isNotBlank(malModifiedPropertyPhotoAsset.getDownloadUrl())) {
                        malAssetEntity = MALAssetEntity.fromMALGetModifiedPropertyPhotos(malModifiedPropertyPhotoAsset, MALAssetType.PHOTO_DOWNLOAD_URL);
                    } else {
                        final String message = String.format("Can not download modified photo [%s]", GSON.toJson(malModifiedPropertyPhotoAsset));
                        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
                        reportsRepository.save( reportsEntity );
                        logger.error(message);
                        return;
                    }
                    malAssetRepository.save(malAssetEntity);
                });
    }
}
