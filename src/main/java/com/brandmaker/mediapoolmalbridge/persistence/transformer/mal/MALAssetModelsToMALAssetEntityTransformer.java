package com.brandmaker.mediapoolmalbridge.persistence.transformer.mal;

import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.MALGetAsset;
import com.brandmaker.mediapoolmalbridge.clients.mal.model.MALAssetType;
import com.brandmaker.mediapoolmalbridge.constants.Constants;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetJsonedValuesEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.StructureType;
import com.brandmaker.mediapoolmalbridge.service.StructuresService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Transforms models between MAL server and {@link AssetEntity}
 */
@Component
public class MALAssetModelsToMALAssetEntityTransformer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final StructuresService structuresService;

    public MALAssetModelsToMALAssetEntityTransformer(final StructuresService structuresService) {
        this.structuresService = structuresService;
    }

    /**
     * Creates {@link AssetEntity} from {@link MALGetAsset}
     *
     * @param malGetAsset
     * @param assetType
     * @return
     */
    public AssetEntity fromMALGetAsset(final MALGetAsset malGetAsset, final MALAssetType assetType) {
        final AssetEntity assetEntity = new AssetEntity();
        String fileNameOnDisc = "";
        String url = "";
        final long instant = Instant.now().toEpochMilli();
        switch (assetType) {
            case FILE:
                if (StringUtils.isNotBlank(malGetAsset.getXlUrl())) {
                    url = malGetAsset.getXlUrl();
                    fileNameOnDisc = Constants.XL_FILE_PREFIX + instant + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getLargeUrl())) {
                    url = malGetAsset.getLargeUrl();
                    fileNameOnDisc = Constants.LARGE_FILE_PREFIX + instant + Constants.SPLITTER + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getHighUrl())) {
                    url = malGetAsset.getHighUrl();
                    fileNameOnDisc = Constants.HIGH_FILE_PREFIX + instant + Constants.SPLITTER + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getMediumUrl())) {
                    url = malGetAsset.getMediumUrl();
                    fileNameOnDisc = Constants.MEDIUM_FILE_PREFIX + instant + Constants.SPLITTER + getFileName(url, malGetAsset);
                } else if (StringUtils.isNotBlank(malGetAsset.getThumbnailUrl())) {
                    url = malGetAsset.getThumbnailUrl();
                    fileNameOnDisc = Constants.THUMBNAIL_FILE_PREFIX + instant + Constants.SPLITTER + getFileName(url, malGetAsset);
                }
                break;
            case JPG_LOGO:
                if (StringUtils.isNotBlank(malGetAsset.getLogoJpgUrl())) {
                    fileNameOnDisc = Constants.LOGO_JPG_FILE_PREFIX + instant + Constants.SPLITTER +
                            getFileNameWithExtension(malGetAsset.getLogoJpgUrl(), malGetAsset, ".jpg");
                    url = malGetAsset.getLogoJpgUrl();
                }
                break;
            case PNG_LOGO:
                if (StringUtils.isNotBlank(malGetAsset.getLogoPngUrl())) {
                    fileNameOnDisc = Constants.LOGO_PNG_FILE_PREFIX + instant + Constants.SPLITTER +
                            getFileNameWithExtension(malGetAsset.getLogoPngUrl(), malGetAsset, ".png");
                    url = malGetAsset.getLogoPngUrl();
                }
                break;
        }
        fileNameOnDisc = fileNameOnDisc.replace(" ", "_");

        assetEntity.setMalAssetId(malGetAsset.getAssetId());
        assetEntity.setAssetType(assetType);
        assetEntity.setCaption(malGetAsset.getCaption());
        assetEntity.setAssetTypeId(malGetAsset.getAssetTypeId());
        assetEntity.setPropertyId(malGetAsset.getPropertyId());
        assetEntity.setMarshaCode(malGetAsset.getMarshaCode());
        assetEntity.setBrandId(malGetAsset.getBrandId());
        assetEntity.setColorId(malGetAsset.getColorId());
        assetEntity.setFileNameOnDisc(fileNameOnDisc);
        assetEntity.setUrl(url);
        assetEntity.setFileNameInMal(malGetAsset.getFilename());
        assetEntity.setMalLastModified(malGetAsset.getLastModified());
        assetEntity.setMalCreated(malGetAsset.getDateCreated());
        assetEntity.setPropertyId(malGetAsset.getPropertyId());
        assetEntity.setMarshaCode(malGetAsset.getMarshaCode());
        assetEntity.setBrandId(malGetAsset.getBrandId());

        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(malGetAsset);
        } catch (final Exception e) {
            logger.error("Object not converted to json", e);
        }
        assetEntity.setMalMd5Hash(DigestUtils.md5Hex(json));
        final AssetJsonedValuesEntity assetJsonedValuesEntity = new AssetJsonedValuesEntity();
        assetJsonedValuesEntity.setMalGetAssetJson(json);
        assetEntity.setAssetJsonedValuesEntity(assetJsonedValuesEntity);

        return assetEntity;
    }

    /**
     * Finds file name with extension of file which is going to be downloaded from MAL server
     *
     * @param url
     * @param malGetAsset
     * @param extension
     * @return
     */
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

    /**
     * Finds file name of the file which is going to be downloaded from MAL server
     *
     * @param url
     * @param malGetAsset
     * @return
     */
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

    /**
     * Finds extension of file which is going to be downloaded from MAL server
     *
     * @param fileTypeId
     * @return
     */
    private String getFileExtension(final String fileTypeId) {
        String fileExtension = structuresService.getStructureByStructureIdAndStructureType(fileTypeId, StructureType.FILETYPE).getStructureName();
        if (!StringUtils.isBlank(fileExtension) &&
                !"mixed".equals(fileExtension) &&
                !"none".equals(fileExtension)) {
            return "." + fileExtension;
        }
        return "";
    }
}
