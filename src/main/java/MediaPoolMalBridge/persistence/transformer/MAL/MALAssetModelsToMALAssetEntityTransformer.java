package MediaPoolMalBridge.persistence.transformer.MAL;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class MALAssetModelsToMALAssetEntityTransformer {

    private final MALAssetStructures malAssetStructures;

    public MALAssetModelsToMALAssetEntityTransformer(final MALAssetStructures malAssetStructures)
    {
        this.malAssetStructures = malAssetStructures;
    }

    public MALAssetEntity fromMALGetAssetUpdate(final MALAssetEntity malAssetEntity, final MALGetAsset malGetAsset, final MALAssetType malAssetType) {
        String fileNameOnDisc = "";
        String url = "";
        switch (malAssetType) {
            case FILE:
                if (StringUtils.isNotBlank(malGetAsset.getXlUrl())) {
                    url = malGetAsset.getXlUrl();
                    fileNameOnDisc = Constants.XL_FILE_PREFIX + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getLargeUrl())) {
                    url = malGetAsset.getLargeUrl();
                    fileNameOnDisc = Constants.LARGE_FILE_PREFIX + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getMediumUrl())) {
                    url = malGetAsset.getMediumUrl();
                    fileNameOnDisc = Constants.MEDIUM_FILE_PREFIX + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getThumbnailUrl())) {
                    url = malGetAsset.getThumbnailUrl();
                    fileNameOnDisc = Constants.THUMBNAIL_FILE_PREFIX + getFileName(url, malGetAsset);
                }
                break;
            case JPG_LOGO:
                if (StringUtils.isNotBlank(malGetAsset.getLogoJpgUrl())) {
                    fileNameOnDisc = Constants.LOGO_JPG_FILE_PREFIX +
                            getFileNameWithExtension(malGetAsset.getLogoJpgUrl(), malGetAsset, ".jpg");
                    url = malGetAsset.getLogoJpgUrl();
                }
                break;
            case PNG_LOGO:
                if (StringUtils.isNotBlank(malGetAsset.getLogoPngUrl())) {
                    fileNameOnDisc = Constants.LOGO_PNG_FILE_PREFIX +
                            getFileNameWithExtension(malGetAsset.getLogoPngUrl(), malGetAsset, ".png");
                    url = malGetAsset.getLogoPngUrl();
                }
                break;
        }
        fileNameOnDisc = fileNameOnDisc.replace(" ", "_");

        malAssetEntity.setMalAssetTypeId( malGetAsset.getAssetTypeId() );
        malAssetEntity.setPropertyId( malGetAsset.getPropertyId() );
        malAssetEntity.setMalColorId( malGetAsset.getColorId() );
        malAssetEntity.setFileNameOnDisc( fileNameOnDisc );
        malAssetEntity.setUrl( url );
        malAssetEntity.setFileNameInMal( malGetAsset.getFilename() );
        malAssetEntity.setMalLastModified( malGetAsset.getLastModified() );
        malAssetEntity.setTransferringAssetStatus( TransferringAssetStatus.MAL_UPDATED );
        malAssetEntity.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.OBSERVED);
        malAssetEntity.setMALGetAsset( malGetAsset );

        return malAssetEntity;
    }

    public MALAssetEntity fromMALGetAssetCreate(final MALGetAsset malGetAsset, final MALAssetType malAssetType) {
        final MALAssetEntity malAssetEntity = new MALAssetEntity();
        String fileNameOnDisc = "";
        String url = "";
        switch (malAssetType) {
            case FILE:
                if (StringUtils.isNotBlank(malGetAsset.getXlUrl())) {
                    url = malGetAsset.getXlUrl();
                    fileNameOnDisc = Constants.XL_FILE_PREFIX + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getLargeUrl())) {
                    url = malGetAsset.getLargeUrl();
                    fileNameOnDisc = Constants.LARGE_FILE_PREFIX + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getMediumUrl())) {
                    url = malGetAsset.getMediumUrl();
                    fileNameOnDisc = Constants.MEDIUM_FILE_PREFIX + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getThumbnailUrl())) {
                    url = malGetAsset.getThumbnailUrl();
                    fileNameOnDisc = Constants.THUMBNAIL_FILE_PREFIX + getFileName(url, malGetAsset);
                }
                break;
            case JPG_LOGO:
                if (StringUtils.isNotBlank(malGetAsset.getLogoJpgUrl())) {
                    fileNameOnDisc = Constants.LOGO_JPG_FILE_PREFIX +
                            getFileNameWithExtension(malGetAsset.getLogoJpgUrl(), malGetAsset, ".jpg");
                    url = malGetAsset.getLogoJpgUrl();
                }
                break;
            case PNG_LOGO:
                if (StringUtils.isNotBlank(malGetAsset.getLogoPngUrl())) {
                    fileNameOnDisc = Constants.LOGO_PNG_FILE_PREFIX +
                            getFileNameWithExtension(malGetAsset.getLogoPngUrl(), malGetAsset, ".png");
                    url = malGetAsset.getLogoPngUrl();
                }
                break;
        }
        fileNameOnDisc = fileNameOnDisc.replace(" ", "_");

        malAssetEntity.setAssetType(malAssetType);
        malAssetEntity.setAssetId(malGetAsset.getAssetId());
        malAssetEntity.setMalAssetTypeId( malGetAsset.getAssetTypeId() );
        malAssetEntity.setPropertyId( malGetAsset.getPropertyId() );
        malAssetEntity.setMalColorId( malGetAsset.getColorId() );
        malAssetEntity.setFileNameOnDisc( fileNameOnDisc );
        malAssetEntity.setUrl( url );
        malAssetEntity.setFileNameInMal( malGetAsset.getFilename() );
        malAssetEntity.setMalLastModified( malGetAsset.getLastModified() );
        malAssetEntity.setBmAssetId("CREATING_" + malAssetEntity.getAssetId());
        malAssetEntity.setTransferringAssetStatus( TransferringAssetStatus.MAL_CREATED );
        malAssetEntity.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.OBSERVED);
        malAssetEntity.setMALGetAsset( malGetAsset );

        return malAssetEntity;
    }


    public MALAssetEntity fromMALGetAssetCreate(final MALAssetEntity malAssetEntity, final MALGetAsset malGetAsset, final MALAssetType malAssetType) {
        String fileNameOnDisc = "";
        String url = "";
        switch (malAssetType) {
            case FILE:
                if (StringUtils.isNotBlank(malGetAsset.getXlUrl())) {
                    url = malGetAsset.getXlUrl();
                    fileNameOnDisc = Constants.XL_FILE_PREFIX + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getLargeUrl())) {
                    url = malGetAsset.getLargeUrl();
                    fileNameOnDisc = Constants.LARGE_FILE_PREFIX + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getMediumUrl())) {
                    url = malGetAsset.getMediumUrl();
                    fileNameOnDisc = Constants.MEDIUM_FILE_PREFIX + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getThumbnailUrl())) {
                    url = malGetAsset.getThumbnailUrl();
                    fileNameOnDisc = Constants.THUMBNAIL_FILE_PREFIX + getFileName(url, malGetAsset);
                }
                break;
            case JPG_LOGO:
                if (StringUtils.isNotBlank(malGetAsset.getLogoJpgUrl())) {
                    fileNameOnDisc = Constants.LOGO_JPG_FILE_PREFIX +
                            getFileNameWithExtension(malGetAsset.getLogoJpgUrl(), malGetAsset, ".jpg");
                    url = malGetAsset.getLogoJpgUrl();
                }
                break;
            case PNG_LOGO:
                if (StringUtils.isNotBlank(malGetAsset.getLogoPngUrl())) {
                    fileNameOnDisc = Constants.LOGO_PNG_FILE_PREFIX +
                            getFileNameWithExtension(malGetAsset.getLogoPngUrl(), malGetAsset, ".png");
                    url = malGetAsset.getLogoPngUrl();
                }
                break;
        }
        fileNameOnDisc = fileNameOnDisc.replace(" ", "_");

        malAssetEntity.setMalAssetTypeId( malGetAsset.getAssetTypeId() );
        malAssetEntity.setPropertyId( malGetAsset.getPropertyId() );
        malAssetEntity.setMalColorId( malGetAsset.getColorId() );
        malAssetEntity.setFileNameOnDisc( fileNameOnDisc );
        malAssetEntity.setUrl( url );
        malAssetEntity.setFileNameInMal( malGetAsset.getFilename() );
        malAssetEntity.setMalLastModified( malGetAsset.getLastModified() );
        malAssetEntity.setTransferringAssetStatus( TransferringAssetStatus.MAL_CREATED );
        malAssetEntity.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.OBSERVED);
        malAssetEntity.setMALGetAsset( malGetAsset );

        return malAssetEntity;
    }

    private String getFileNameWithExtension(final String url, final MALGetAsset malGetAsset, final String extension) {
        final String fileName = StringUtils.substringBefore(url, "?");
        if (StringUtils.isNotBlank(fileName)) {
            final String[] parts = fileName.split("/");
            return parts[parts.length - 1];
        }
        if (StringUtils.isNotBlank(malGetAsset.getFilename())) {
            return malGetAsset.getFilename() + extension;
        }
        return RandomStringUtils.randomAlphabetic(30) + extension;
    }

    private String getFileName(final String url, final MALGetAsset malGetAsset) {
        final String fileName = StringUtils.substringBefore(url, "?");
        if (StringUtils.isNotBlank(fileName)) {
            final String[] parts = fileName.split("/");
            return parts[parts.length - 1];
        }
        if (StringUtils.isNotBlank(malGetAsset.getFilename())) {
            return malGetAsset.getFilename() + getFileExtension(malGetAsset.getFileTypeId());
        }
        return malGetAsset.getAssetId() + getFileExtension(malGetAsset.getFileTypeId());
    }

    private String getFileExtension(final String fileTypeId) {
        String fileExtension = malAssetStructures.getFileTypes().get(fileTypeId);
        if (!StringUtils.isBlank(fileExtension) &&
                !"mixed".equals(fileExtension) &&
                !"none".equals(fileExtension)) {
            return "." + fileExtension;
        }
        return "";
    }
}
