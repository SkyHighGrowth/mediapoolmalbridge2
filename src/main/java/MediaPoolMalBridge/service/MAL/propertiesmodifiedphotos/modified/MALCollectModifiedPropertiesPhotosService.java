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
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class MALCollectModifiedPropertiesPhotosService extends AbstractMALUniqueService {

    private final MALGetModifiedPropertyPhotoClient getModifiedPropertyPhotoClient;

    private String modifiedSince;

    public MALCollectModifiedPropertiesPhotosService(final MALGetModifiedPropertyPhotoClient getModifiedPropertyPhotoClient) {
        this.getModifiedPropertyPhotoClient = getModifiedPropertyPhotoClient;
    }

    @Override
    protected void run() {
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

    public String getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }
}
