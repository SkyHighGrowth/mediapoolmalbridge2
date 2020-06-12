package MediaPoolMalBridge.service.MAL.assets.transformer;

        import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
        import MediaPoolMalBridge.model.MAL.MALAssetStructures;
        import MediaPoolMalBridge.service.BrandMaker.theme.themeid.model.BMThemePathToId;
        import com.brandmaker.webservices.mediapool.LanguageItem;
        import com.brandmaker.webservices.mediapool.ThemeDto;
        import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
        import org.apache.commons.lang3.StringUtils;
        import org.springframework.stereotype.Component;

/**
 * Transformer that transforms {@link MALGetAsset} into {@link UploadMetadataArgument}
 */
@Component
public class MALToBMTransformer {

    private MALAssetStructures assetStructures;

    private BMThemePathToId bmThemePathToId;

    public MALToBMTransformer(final MALAssetStructures assetStructures,
                              final BMThemePathToId bmThemePathToId) {
        this.assetStructures = assetStructures;
        this.bmThemePathToId = bmThemePathToId;
    }

    public UploadMetadataArgument transformToUploadMetadataArgument(final MALGetAsset malGetAsset) {

        final UploadMetadataArgument uploadMetadataArgument = new UploadMetadataArgument();

        uploadMetadataArgument.setAddAssociations(true);
        uploadMetadataArgument.setVirtualDbName("Standard");
        uploadMetadataArgument.setShow("SHOW_ALWAYS");
        uploadMetadataArgument.setCreatorName("Soap Api");
        uploadMetadataArgument.setDesignationType("3");
        uploadMetadataArgument.setFileName(malGetAsset.getFilename());

        if (StringUtils.isNotBlank(malGetAsset.getMarshaCode())) {
            uploadMetadataArgument.setSelectedAffiliate(malGetAsset.getMarshaCode());
        } else if (StringUtils.isNotBlank(malGetAsset.getPropertyId())) {
            uploadMetadataArgument.setSelectedAffiliate(malGetAsset.getPropertyId());
        }

        ThemeDto themeDto = new ThemeDto();
        themeDto.setName("Implementation/Pictures");
        uploadMetadataArgument.getAssociations().add(themeDto);

        String temp = assetStructures.getAssetTypes().get(malGetAsset.getAssetTypeId());
        if (StringUtils.isNotBlank(temp)) {
            themeDto = new ThemeDto();
            themeDto.setName("Asset Types/" + temp);
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        temp = assetStructures.getBrands().get(malGetAsset.getBrandId());
        if (StringUtils.isNotEmpty(temp)) {
            themeDto = new ThemeDto();
            themeDto.setName("Brands/" + temp);
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        temp = assetStructures.getCollections().get(malGetAsset.getCollectionId());
        if (StringUtils.isNotEmpty(temp)) {
            themeDto = new ThemeDto();
            themeDto.setName("Collections/" + temp);
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        temp = assetStructures.getDestinations().get(malGetAsset.getDestionationId());
        if (StringUtils.isNotEmpty(temp)) {
            themeDto = new ThemeDto();
            bmThemePathToId.addThemePath("Destinations/" + temp);
            themeDto.setName(temp);
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        temp = assetStructures.getSubjects().get(malGetAsset.getSubjectId());
        if (StringUtils.isNotEmpty(temp)) {
            themeDto = new ThemeDto();
            themeDto.setName("Subjects/" + temp);
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        temp = assetStructures.getColors().get(malGetAsset.getColorId());
        if (StringUtils.isNotEmpty(temp)) {
            themeDto = new ThemeDto();
            themeDto.setName("Colors/" + temp);
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

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

        languageItem = new LanguageItem();
        languageItem.setDescription(malGetAsset.getAssetId());
        languageItem.setLangCode("EN");
        uploadMetadataArgument.getFreeField1().add(languageItem);

        languageItem = new LanguageItem();
        languageItem.setDescription(malGetAsset.getAssociation());
        languageItem.setLangCode("EN");
        uploadMetadataArgument.getFreeField2().add(languageItem);

        if (StringUtils.isNotBlank(malGetAsset.getMarshaCode())) {
            languageItem = new LanguageItem();
            languageItem.setDescription(malGetAsset.getMarshaCode());
            languageItem.setLangCode("EN");
            uploadMetadataArgument.getFreeField3().add(languageItem);
        } else if (StringUtils.isNotBlank(malGetAsset.getPropertyId())) {
            languageItem = new LanguageItem();
            languageItem.setDescription(malGetAsset.getPropertyId());
            languageItem.setLangCode("EN");
            uploadMetadataArgument.getFreeField3().add(languageItem);
        }

        languageItem = new LanguageItem();
        languageItem.setDescription(malGetAsset.getName());
        languageItem.setLangCode("EN");
        uploadMetadataArgument.getFreeField4().add(languageItem);

        if (malGetAsset.isLimitedRights()) {
            languageItem = new LanguageItem();
            languageItem.setDescription(toZeroAndOne(malGetAsset.isLimitedRights()));
            languageItem.setLangCode("EN");
            uploadMetadataArgument.getFreeField5().add(languageItem);
        }

        if (StringUtils.isNotBlank(malGetAsset.getUsageDescription())) {
            languageItem = new LanguageItem();
            languageItem.setDescription(String.valueOf(malGetAsset.getUsageDescription()));
            languageItem.setLangCode("EN");
            uploadMetadataArgument.getFreeField6().add(languageItem);
        }

        if (StringUtils.isNotBlank(malGetAsset.getInstructions())) {
            languageItem = new LanguageItem();
            languageItem.setDescription(String.valueOf(malGetAsset.getInstructions()));
            languageItem.setLangCode("EN");
            uploadMetadataArgument.getFreeField7().add(languageItem);
        }

        if (malGetAsset.isRightsManaged()) {
            languageItem = new LanguageItem();
            languageItem.setDescription(toZeroAndOne(malGetAsset.isRightsManaged()));
            languageItem.setLangCode("EN");
            uploadMetadataArgument.getFreeField8().add(languageItem);
        }

        return uploadMetadataArgument;
    }

    private String toZeroAndOne(final boolean value) {
        return value ? "1" : "0";
    }
}
