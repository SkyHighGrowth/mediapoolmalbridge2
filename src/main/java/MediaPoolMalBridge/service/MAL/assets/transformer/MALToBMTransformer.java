package MediaPoolMalBridge.service.MAL.assets.transformer;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import com.brandmaker.webservices.mediapool.LanguageItem;
import com.brandmaker.webservices.mediapool.ThemeDto;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class MALToBMTransformer {

    private MALAssetStructures assetStructures;

    public MALToBMTransformer(final MALAssetStructures assetStructures) {
        this.assetStructures = assetStructures;
    }

    public UploadMetadataArgument transformToUploadMetadataArgument(final MALGetAsset malGetAsset ) {

        final UploadMetadataArgument uploadMetadataArgument = new UploadMetadataArgument();
        //TODO set Media number
        uploadMetadataArgument.setAddAssociations( false );
        uploadMetadataArgument.setVirtualDbName( "Standard" );
        uploadMetadataArgument.setShow( "SHOW_ALWAYS" );
        uploadMetadataArgument.setCreatorName( "Soap Api" );
        uploadMetadataArgument.setDesignationType( "3" );

        ThemeDto themeDto = new ThemeDto();
        themeDto.setName( "Implementation/Pictures" );
        uploadMetadataArgument.getAssociations().add( themeDto );

        if (StringUtils.isNotEmpty( malGetAsset.getAssetTypeId() ) ) {
            themeDto = new ThemeDto();
            themeDto.setName( "Asset Types/" + assetStructures.getAssetTypes().get( malGetAsset.getAssetTypeId() ) );
            uploadMetadataArgument.getAssociations().add( themeDto );
        }

        if (StringUtils.isNotEmpty( malGetAsset.getBrandId() ) ) {
            themeDto = new ThemeDto();
            themeDto.setName( "Brands/" + assetStructures.getBrands().get( malGetAsset.getBrandId() ) );
            uploadMetadataArgument.getAssociations().add( themeDto );
        }

        if (StringUtils.isNotEmpty( malGetAsset.getCollectionId() ) ) {
            themeDto = new ThemeDto();
            themeDto.setName( "Collections/" + assetStructures.getCollections().get( malGetAsset.getCollectionId() ) );
            uploadMetadataArgument.getAssociations().add( themeDto );
        }

        if (StringUtils.isNotEmpty( malGetAsset.getDestionationId() ) ) {
            themeDto = new ThemeDto();
            themeDto.setName( "Destinations/" + assetStructures.getDestinations().get( malGetAsset.getDestionationId() ) );
            uploadMetadataArgument.getAssociations().add( themeDto );
        }

        if (StringUtils.isNotEmpty( malGetAsset.getSubjectId() ) ) {
            themeDto = new ThemeDto();
            themeDto.setName( "Subjects/" + assetStructures.getSubjects().get( malGetAsset.getSubjectId() ) );
            uploadMetadataArgument.getAssociations().add( themeDto );
        }

        if (StringUtils.isNotEmpty( malGetAsset.getColorId() ) ) {
            themeDto = new ThemeDto();
            themeDto.setName( "Colors/" + assetStructures.getColors().get( malGetAsset.getColorId() ) );
            uploadMetadataArgument.getAssociations().add( themeDto );
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

        languageItem = new LanguageItem();
        languageItem.setDescription(String.valueOf(malGetAsset.isLimitedRights()));
        languageItem.setLangCode("EN");
        uploadMetadataArgument.getFreeField5().add(languageItem);

        languageItem = new LanguageItem();
        languageItem.setDescription(String.valueOf(malGetAsset.getUsageDescription()));
        languageItem.setLangCode("EN");
        uploadMetadataArgument.getFreeField6().add(languageItem);

        languageItem = new LanguageItem();
        languageItem.setDescription(String.valueOf(malGetAsset.getInstructions()));
        languageItem.setLangCode("EN");
        uploadMetadataArgument.getFreeField7().add(languageItem);

        languageItem = new LanguageItem();
        languageItem.setDescription(String.valueOf(malGetAsset.isRightsManaged()));
        languageItem.setLangCode("EN");
        uploadMetadataArgument.getFreeField8().add(languageItem);

        return uploadMetadataArgument;
    }
}
