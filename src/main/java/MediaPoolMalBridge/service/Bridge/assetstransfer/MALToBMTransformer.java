package MediaPoolMalBridge.service.Bridge.assetstransfer;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.ReportsRepository;
import com.brandmaker.webservices.mediapool.LanguageItem;
import com.brandmaker.webservices.mediapool.ThemeDto;
import com.brandmaker.webservices.mediapool.UploadMetadataArgument;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MALToBMTransformer {

    private static Logger logger = LoggerFactory.getLogger(MALToBMTransformer.class);

    private MALAssetStructures malAssetStructures;

    private final ReportsRepository reportsRepository;

    public MALToBMTransformer(final MALAssetStructures malAssetStructures,
                              final ReportsRepository reportsRepository) {
        this.malAssetStructures = malAssetStructures;
        this.reportsRepository = reportsRepository;
    }

    public BMAssetEntity transform(final MALAssetEntity malAsset) {
        final BMAssetEntity bmAsset = new BMAssetEntity();
        bmAsset.setTransferringConnectionAssetStatus(malAsset.getTransferringAssetStatus());
        bmAsset.setTransferringMALConnectionAssetStatus(malAsset.getTransferringMALConnectionAssetStatus());
        bmAsset.setMalAssetType(malAsset.getAssetType());
        bmAsset.setAssetId(malAsset.getBmAssetId());

        switch (malAsset.getTransferringAssetStatus()) {
            case MAL_CREATED:
            case MAL_UPDATED:
                if (malAsset.getMALGetAsset() != null) {
                    transformMalGetAsset(bmAsset, malAsset);
                } else {
                    bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.INVALID);
                    bmAsset.setTransferringConnectionAssetStatus(TransferringAssetStatus.INVALID);
                    final String message = String.format("Detected MAL asset with id [%s] which can not be translated in mediapool asset", malAsset.getAssetId() );
                    final ReportsEntity reportsEntity = new ReportsEntity( ReportType.WARNING, getClass().getName(), message, ReportTo.BM, (new Gson()).toJson(malAsset), null, null );
                    reportsRepository.save( reportsEntity );
                    logger.error("{}, asset {}", message, (new Gson()).toJson(malAsset) );
                }
                return bmAsset;
            case MAL_DELETED:
                return bmAsset;
        }
        return null;
    }

    private void transformMalGetAsset(final BMAssetEntity bmAsset, final MALAssetEntity malAsset) {
        if (StringUtils.isNotBlank(malAsset.getFileNameOnDisc())) {
            bmAsset.setFileName(malAsset.getFileNameOnDisc());
        } else  {
            final String message = String.format("Detected mal asset without downloaded url mal asset id [%s]", malAsset.getAssetId());
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.WARNING, getClass().getName(), message, ReportTo.BM, (new Gson()).toJson(malAsset), null, null );
            reportsRepository.save( reportsEntity );
            logger.error("{}, asset {}", message, (new Gson()).toJson(malAsset) );
            bmAsset.setTransferringConnectionAssetStatus(TransferringAssetStatus.INVALID);
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.INVALID);
            malAsset.setTransferringAssetStatus(TransferringAssetStatus.INVALID);
            malAsset.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.INVALID);
            return;
        }

        final MALGetAsset malGetAsset = malAsset.getMALGetAsset();
        bmAsset.setMalAssetId( malGetAsset.getAssetId() );
        bmAsset.setCaption( malGetAsset.getCaption() );
        bmAsset.setPropertyId( malGetAsset.getPropertyId() );
        bmAsset.setMalAssetTypeId( malGetAsset.getAssetTypeId() );
        bmAsset.setMalColorId( malGetAsset.getColorId() );
        bmAsset.setMalLastModified(malGetAsset.getLastModified());
        bmAsset.setMalCreated(malGetAsset.getDateCreated());

        final UploadMetadataArgument uploadMetadataArgument = new UploadMetadataArgument();
        //TODO set Media number
        uploadMetadataArgument.setAddAssociations(false);
        uploadMetadataArgument.setVirtualDbName("Standard");
        uploadMetadataArgument.setShow("SHOW_ALWAYS");
        uploadMetadataArgument.setCreatorName("Soap Api");
        uploadMetadataArgument.setDesignationType("3");

        ThemeDto themeDto = new ThemeDto();
        themeDto.setName("Implementation/Pictures");
        uploadMetadataArgument.getAssociations().add(themeDto);

        if (StringUtils.isNotEmpty(malGetAsset.getAssetTypeId())) {
            themeDto = new ThemeDto();
            themeDto.setName("Asset Types/" + malAssetStructures.getAssetTypes().get(malGetAsset.getAssetTypeId()));
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        if (StringUtils.isNotEmpty(malGetAsset.getBrandId())) {
            themeDto = new ThemeDto();
            themeDto.setName("Brands/" + malAssetStructures.getBrands().get(malGetAsset.getBrandId()));
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        if (StringUtils.isNotEmpty(malGetAsset.getCollectionId())) {
            themeDto = new ThemeDto();
            themeDto.setName("Collections/" + malAssetStructures.getCollections().get(malGetAsset.getCollectionId()));
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        if (StringUtils.isNotEmpty(malGetAsset.getDestionationId())) {
            themeDto = new ThemeDto();
            themeDto.setName("Destinations/" + malAssetStructures.getDestinations().get(malGetAsset.getDestionationId()));
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        if (StringUtils.isNotEmpty(malGetAsset.getSubjectId())) {
            themeDto = new ThemeDto();
            themeDto.setName("Subjects/" + malAssetStructures.getSubjects().get(malGetAsset.getSubjectId()));
            uploadMetadataArgument.getAssociations().add(themeDto);
        }

        if (StringUtils.isNotEmpty(malGetAsset.getColorId())) {
            themeDto = new ThemeDto();
            themeDto.setName("Colors/" + malAssetStructures.getColors().get(malGetAsset.getColorId()));
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
        bmAsset.setUploadMetadataArgument(uploadMetadataArgument);
    }
}
