package com.brandmaker.mediapoolmalbridge.service.mal.assets.transformer;

import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.MALGetAsset;
import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.StructureType;
import com.brandmaker.mediapoolmalbridge.service.StructuresService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.theme.themeid.model.BMThemePathToId;
import com.brandmaker.webservices.mediapool.LanguageItem;
import com.brandmaker.webservices.mediapool.ThemeDto;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Transformer that transforms {@link MALGetAsset} into {@link UploadMetadataArgument}
 */
@Component
public class MALToBMTransformer {

    private final BMThemePathToId bmThemePathToId;

    private final AppConfig appConfig;

    private final StructuresService structuresService;

    public MALToBMTransformer(final BMThemePathToId bmThemePathToId, AppConfig appConfig,
                              final StructuresService structuresService) {
        this.bmThemePathToId = bmThemePathToId;
        this.appConfig = appConfig;
        this.structuresService = structuresService;
    }

    public UploadMetadataArgument transformToUploadMetadataArgument(final MALGetAsset malGetAsset) {

        final UploadMetadataArgument uploadMetadataArgument = new UploadMetadataArgument();

        uploadMetadataArgument.setAddAssociations(true);
        setVirtualDbName(uploadMetadataArgument, malGetAsset);
        uploadMetadataArgument.setShow("SHOW_ALWAYS");
        uploadMetadataArgument.setCreatorName("Soap Api");
        uploadMetadataArgument.setDesignationType("3");
        uploadMetadataArgument.setFileName(malGetAsset.getFilename());

        setSelectedAffiliate(uploadMetadataArgument, malGetAsset);

        ThemeDto themeDto = new ThemeDto();
        themeDto.setName("Implementation/Pictures");
        uploadMetadataArgument.getAssociations().add(themeDto);

        String assetType = structuresService.getStructureByStructureIdAndStructureType(malGetAsset.getAssetTypeId(), StructureType.ASSETTYPE).getStructureName();
        setAssociation(uploadMetadataArgument, "Asset Types/", assetType, StringUtils.isNotBlank(assetType));

        String brandId = structuresService.getStructureByStructureIdAndStructureType(malGetAsset.getBrandId(), StructureType.BRAND).getStructureName();
        setAssociation(uploadMetadataArgument, "Brands/", brandId, StringUtils.isNotEmpty(brandId));

        String collectionId = structuresService.getStructureByStructureIdAndStructureType(malGetAsset.getCollectionId(), StructureType.COLLECTION).getStructureName();
        setAssociation(uploadMetadataArgument, "Collections/", collectionId, StringUtils.isNotEmpty(collectionId));

        String destinationId = structuresService.getStructureByStructureIdAndStructureType(malGetAsset.getDestionationId(), StructureType.DESTINATION).getStructureName();
        if (StringUtils.isNotEmpty(destinationId)) {
            bmThemePathToId.addThemePath("Destinations/" + destinationId);
            setAssociation(uploadMetadataArgument, "Destinations/", destinationId, true);
        }

        String subjectId = structuresService.getStructureByStructureIdAndStructureType(malGetAsset.getSubjectId(), StructureType.SUBJECT).getStructureName();
        setAssociation(uploadMetadataArgument, "Subjects/", subjectId, StringUtils.isNotEmpty(subjectId));

        String colorId = structuresService.getStructureByStructureIdAndStructureType(malGetAsset.getColorId(), StructureType.COLOR).getStructureName();
        if (StringUtils.isNotEmpty(colorId)) {
            setAssociation(uploadMetadataArgument, "Colors/", colorId, true);
            setFreeFieldValue(uploadMetadataArgument.getFreeField10(), "MAL_" + colorId);
        }

        // If asset theme is not set, fail safe value is set
        if (StringUtils.isEmpty(collectionId) && StringUtils.isEmpty(destinationId)
                && StringUtils.isEmpty(subjectId) && StringUtils.isEmpty(colorId)) {
            themeDto = new ThemeDto();
            themeDto.setName(appConfig.getAppConfigData().getFailSaveCategoryName());
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        setMediaTitleAndKeyword(uploadMetadataArgument, malGetAsset);

        setFreeFieldValue(uploadMetadataArgument.getFreeField1(), malGetAsset.getAssetId());

        setFreeFieldValue(uploadMetadataArgument.getFreeField2(), malGetAsset.getAssociation());

        if (StringUtils.isNotBlank(malGetAsset.getMarshaCode()) && StringUtils.isNotBlank(malGetAsset.getPropertyId())) {
            setFreeFieldValue(uploadMetadataArgument.getFreeField3(), malGetAsset.getMarshaCode() + "," + malGetAsset.getPropertyId());
        } else if (StringUtils.isNotBlank(malGetAsset.getMarshaCode())) {
            setFreeFieldValue(uploadMetadataArgument.getFreeField3(), malGetAsset.getMarshaCode());
        } else if (StringUtils.isNotBlank(malGetAsset.getPropertyId())) {
            setFreeFieldValue(uploadMetadataArgument.getFreeField3(), malGetAsset.getPropertyId());
        }

        setFreeFieldValue(uploadMetadataArgument.getFreeField4(), malGetAsset.getName());

        if (malGetAsset.isLimitedRights()) {
            setFreeFieldValue(uploadMetadataArgument.getFreeField5(), toZeroAndOne(malGetAsset.isLimitedRights()));
        }

        if (StringUtils.isNotBlank(malGetAsset.getUsageDescription())) {
            setFreeFieldValue(uploadMetadataArgument.getFreeField6(), String.valueOf(malGetAsset.getUsageDescription()));
        }

        if (StringUtils.isNotBlank(malGetAsset.getInstructions())) {
            setFreeFieldValue(uploadMetadataArgument.getFreeField7(), String.valueOf(malGetAsset.getInstructions()));
        }

        if (malGetAsset.isRightsManaged()) {
            setFreeFieldValue(uploadMetadataArgument.getFreeField8(), toZeroAndOne(malGetAsset.isRightsManaged()));
        }

        if (malGetAsset.isHws()) {
            setFreeFieldValue(uploadMetadataArgument.getFreeField11(), "HWS");
        } else {
            setFreeFieldValue(uploadMetadataArgument.getFreeField11(), "Legacy");
        }


        return uploadMetadataArgument;
    }

    private void setMediaTitleAndKeyword(UploadMetadataArgument uploadMetadataArgument, MALGetAsset malGetAsset) {
        LanguageItem languageItem = new LanguageItem();
        if (StringUtils.isNotBlank(malGetAsset.getMetadata())) {
            languageItem.setDescription(malGetAsset.getMetadata());
            languageItem.setLangCode("EN");
        } else if (StringUtils.isNotBlank(malGetAsset.getCaption())) {
            languageItem.setDescription(malGetAsset.getCaption());
            languageItem.setLangCode("EN");
        } else {
            languageItem.setDescription(malGetAsset.getAssetId());
            languageItem.setLangCode("EN");
        }
        uploadMetadataArgument.getMediaTitle().add(languageItem);
        uploadMetadataArgument.getKeyword().add(languageItem);
    }

    private void setSelectedAffiliate(UploadMetadataArgument uploadMetadataArgument, MALGetAsset malGetAsset) {
        if (StringUtils.isNotBlank(malGetAsset.getPropertyId())) {
            uploadMetadataArgument.setSelectedAffiliate(malGetAsset.getPropertyId());
        } else if (StringUtils.isNotBlank(malGetAsset.getMarshaCode())) {
            uploadMetadataArgument.setSelectedAffiliate(malGetAsset.getMarshaCode());
        }
    }

    private void setFreeFieldValue(List<LanguageItem> freeField, String description) {
        LanguageItem languageItem = new LanguageItem();
        languageItem.setDescription(description);
        languageItem.setLangCode("EN");
        freeField.add(languageItem);
    }

    private void setVirtualDbName(UploadMetadataArgument uploadMetadataArgument, MALGetAsset malGetAsset) {
        if (malGetAsset.getStatus().equals("active")) {
            uploadMetadataArgument.setVirtualDbName("Standard");
        } else {
            uploadMetadataArgument.setVirtualDbName("SAL Recycle bin");
        }
    }

    private void setAssociation(UploadMetadataArgument uploadMetadataArgument, String themePath, String collectionId, boolean notEmpty) {
        if (notEmpty) {
            ThemeDto themeDto = new ThemeDto();
            themeDto.setName(themePath + collectionId);
            uploadMetadataArgument.getAssociations().add(themeDto);
        }
    }

    private String toZeroAndOne(final boolean value) {
        return value ? "1" : "0";
    }
}
