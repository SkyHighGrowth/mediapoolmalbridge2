package MediaPoolMalBridge.persistence.transformer.MAL;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class MALAssetModelsToMALAssetEntityTransformer {

    private final MALAssetStructures assetStructures;

    public MALAssetModelsToMALAssetEntityTransformer(final MALAssetStructures assetStructures)
    {
        this.assetStructures = assetStructures;
    }

    public AssetEntity fromMALGetAsset( final MALGetAsset malGetAsset, final MALAssetType assetType )
    {
        final AssetEntity assetEntity = new AssetEntity();
        String fileNameOnDisc = "";
        String url = "";
        switch (assetType) {
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

        assetEntity.setMalAssetId( malGetAsset.getAssetId() );
        assetEntity.setAssetType( assetType );
        assetEntity.setCaption( malGetAsset.getCaption() );
        assetEntity.setMalAssetTypeId( malGetAsset.getAssetTypeId() );
        assetEntity.setPropertyId( malGetAsset.getPropertyId() );
        assetEntity.setMarshaCode( malGetAsset.getMarshaCode() );
        assetEntity.setBrandId( malGetAsset.getBrandId() );
        assetEntity.setColorId( malGetAsset.getColorId() );
        assetEntity.setFileNameOnDisc( fileNameOnDisc );
        assetEntity.setUrl( url );
        assetEntity.setFileNameInMal( malGetAsset.getFilename() );
        assetEntity.setMalLastModified( malGetAsset.getLastModified() );
        assetEntity.setMalCreated( malGetAsset.getDateCreated() );
        assetEntity.setPropertyId( malGetAsset.getPropertyId() );
        assetEntity.setMarshaCode( malGetAsset.getMarshaCode() );
        assetEntity.setBrandId( malGetAsset.getBrandId() );
        assetEntity.setMALGetAsset( malGetAsset );

        return assetEntity;
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
        String fileExtension = assetStructures.getFileTypes().get(fileTypeId);
        if (!StringUtils.isBlank(fileExtension) &&
            !"mixed".equals(fileExtension) &&
            !"none".equals(fileExtension)) {
            return "." + fileExtension;
        }
        return "";
    }
}
