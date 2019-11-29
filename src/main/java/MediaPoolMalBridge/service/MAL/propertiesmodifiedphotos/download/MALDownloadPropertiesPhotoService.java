package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.download;

import MediaPoolMalBridge.clients.MAL.download.client.MALDownloadAssetClient;
import MediaPoolMalBridge.clients.MAL.download.client.model.MALDownloadAssetResponse;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALModifiedPropertyPhotoAsset;
import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALNonUniqueThreadService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class MALDownloadPropertiesPhotoService extends AbstractMALNonUniqueThreadService<AssetEntity> {

    private static Logger logger = LoggerFactory.getLogger(MALDownloadPropertiesPhotoService.class);

    private static final Gson GSON = new Gson();

    private final MALDownloadAssetClient malDownloadAssetClient;

    public MALDownloadPropertiesPhotoService(final MALDownloadAssetClient malDownloadAssetClient) {
        this.malDownloadAssetClient = malDownloadAssetClient;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        assetEntity.increaseMalStatesRepetitions();
        final TransferringAssetStatus transferringAssetStatus = assetEntity.getTransferringAssetStatus();
        assetEntity.setTransferringAssetStatus( TransferringAssetStatus.FILE_DELETING );
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for downloading photo achieved for asset id [%s]", assetEntity.getMalAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(assetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            return;
        }
        final MALModifiedPropertyPhotoAsset malModifiedPropertyPhotoAsset = assetEntity.getMALModifiedPropertyPhotoAsset();
        final String fileName = malModifiedPropertyPhotoAsset.getFilename();

        if (StringUtils.isBlank(fileName)) {
            logger.error("Empty file name for MAL asset {}", (new Gson()).toJson(malModifiedPropertyPhotoAsset));
            return;
        }

        MALDownloadAssetResponse response;
        boolean downloaded = true;
        try {
            if (StringUtils.isNotBlank(malModifiedPropertyPhotoAsset.getMediumDownloadUrl())) {
                final String mediumFileName = Constants.MEDIUM_PHOTO_FILE_PREFIX + fileName;
                response = fire(decode(malModifiedPropertyPhotoAsset.getMediumDownloadUrl()), mediumFileName);
                if (response.isSuccess()) {
                    assetEntity.setFileNameOnDisc(mediumFileName);
                } else {
                    logger.error("Can not download medium file for property phot {}, with message {}",
                            GSON.toJson(malModifiedPropertyPhotoAsset),
                            response.getMessage());
                    downloaded = false;
                }
            }

            if (!downloaded && StringUtils.isNotBlank(malModifiedPropertyPhotoAsset.getJpgDownloadUrl())) {
                final String jpgFileName = Constants.JPG_PHOTO_FILE_PREFIX + fileName;
                response = fire(decode(malModifiedPropertyPhotoAsset.getJpgDownloadUrl()), jpgFileName);
                if (response.isSuccess()) {
                    assetEntity.setFileNameOnDisc(jpgFileName);
                } else {
                    logger.error("Can not download JPG file for property photo {} with message {}",
                            GSON.toJson(malModifiedPropertyPhotoAsset),
                            response.getMessage());
                    downloaded = false;
                }
            }

            if (downloaded) {
                assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_DOWNLOADED);
            } else {
                assetEntity.setTransferringAssetStatus(transferringAssetStatus);
            }
        } catch (final Exception e) {
            logger.error("Can not create job for asset {}", (new Gson()).toJson(malModifiedPropertyPhotoAsset), e);
        }
    }

    private MALDownloadAssetResponse fire(final String url, final String fileName) {
        return malDownloadAssetClient.download(url, fileName);
    }

    private String decode(final String encoded)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
    }
}
