package com.brandmaker.mediapoolmalbridge.service.mal.assets;

import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.MALGetAssetsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.MALGetNewBrandAssetsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.*;
import com.brandmaker.mediapoolmalbridge.clients.mal.model.MALAssetType;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import com.brandmaker.mediapoolmalbridge.config.AppConfigData;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bm.BMAssetIdEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetJsonedValuesEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.MALAssetOperation;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bm.BMAssetIdRepository;
import com.brandmaker.mediapoolmalbridge.persistence.transformer.mal.MALAssetModelsToMALAssetEntityTransformer;
import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.transformer.MALToBMTransformer;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Class that collects common fields and methods of services that collects data from MAL server
 */
public abstract class AbstractMALAssetsUniqueThreadService extends AbstractMALUniqueThreadService {

    public static final String EPS_FILE_FORMAT = ".eps?";
    public static final String ZIP_FILE_FORMAT = ".zip?";
    public static final String LOGO_ASSET_TYPE_ID = "2";
    @Autowired
    private MALGetAssetsClient getAssetsClient;

    @Autowired
    private MALAssetModelsToMALAssetEntityTransformer assetModelsToMALAssetEntityTransformer;

    @Autowired
    private MALToBMTransformer malToBMTransformer;

    @Autowired
    private BMAssetIdRepository bmAssetIdRepository;

    @Autowired
    private MALGetNewBrandAssetsClient getNewAssetsClient;

    private String since;

    protected void downloadAssets(final MALGetAssetsRequest request) {
        request.setPage(1);

        //filter based on the properties from application.properties.json file
        AppConfigData appConfigData = appConfig.getAppConfigData();
        List<String> filterOnlyMalProperties = appConfigData.getFilterOnlyMalProperties();
        if (filterOnlyMalProperties != null && !filterOnlyMalProperties.isEmpty()) {
            request.setBrandIds(filterOnlyMalProperties);
        }

        List<String> filterOnlyAssetType = appConfigData.getFilterOnlyAssetType();
        if (filterOnlyAssetType != null && !filterOnlyAssetType.isEmpty()) {
            request.setAssetTypeIds(filterOnlyAssetType);
        }

        List<String> filterOnlyColorIds = appConfigData.getFilterOnlyColorIds();
        if (filterOnlyColorIds != null && !filterOnlyColorIds.isEmpty()) {
            request.setColorIds(filterOnlyColorIds);
        }


        RestResponse<MALGetAssetsResponse> response = getAssetsClient.download(request);
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                StringUtils.isBlank(response.getResponse().getTotalPages()) ||
                response.getResponse().getAssets() == null) {
            return;
        }
        String jsonResponse = GSON.toJson(response);
        logger.debug("response asset created, modified {}", jsonResponse);
        final int totalPages;
        try {
            totalPages = Integer.parseInt(response.getResponse().getTotalPages());
        } catch (final Exception e) {
            final String message = String.format("Can not parse totalPages of created asset since [%s], with httpStatus [%s], and responseBody [%s]",
                    since,
                    response.getHttpStatus(),
                    GSON.toJson(response.getResponse()));
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message, e);
            return;
        }

        transformPagesIntoAssets(request, totalPages);
    }

    protected void downloadNewAssets(final MALGetNewBrandAssetsRequest request) {
        request.setPage(1);

        RestResponse<MALGetNewAssetsResponse> response = getNewAssetsClient.download(request);
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getMeta() == null ||
                response.getResponse().getMeta().getTotal() == null ||
                response.getResponse().getData() == null) {
            return;
        }
        String jsonResponse = GSON.toJson(response);
        logger.debug("response asset created, modified {}", jsonResponse);
        final int totalPages;
        try {
            totalPages = response.getResponse().getMeta().getTotal() / response.getResponse().getMeta().getLimit();
        } catch (final Exception e) {
            final String message = String.format("Can not parse totalPages of created asset since [%s], with httpStatus [%s], and responseBody [%s]",
                    since,
                    response.getHttpStatus(),
                    GSON.toJson(response.getResponse()));
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message, e);
            return;
        }

        transformPagesIntoNewAssets(request, totalPages);
    }

    private void transformPagesIntoNewAssets(final MALGetNewBrandAssetsRequest request, final int totalPages) {
        Map<String, String> includedAssetTypes = appConfig.getAppConfigData().getIncludedAssetTypes();
        for (int page = 0; page < totalPages; ++page) {
            try {
                request.setPage(page + 1);
                RestResponse<MALGetNewAssetsResponse> response = getNewAssetsClient.download(request);
                if (!response.isSuccess() ||
                        response.getResponse() == null ||
                        response.getResponse().getData() == null) {
                    continue;
                }

                transformPagesIntoNewAssets(response.getResponse().getData(), includedAssetTypes);
            } catch (final Exception e) {
                final String message = String.format("Problem storing page [%s] to database with message [%s]", page, e.getMessage());
                final ReportsEntity reportsEntity = new ReportsEntity(ReportType.WARNING, getClass().getName(), null, message, ReportTo.BM, null, null, null);
                reportsRepository.save(reportsEntity);
                logger.error(message);
            }
        }
    }

    private void transformPagesIntoAssets(final MALGetAssetsRequest request, final int totalPages) {
        Map<String, String> includedAssetTypes = appConfig.getAppConfigData().getIncludedAssetTypes();
        for (int page = 0; page < totalPages; ++page) {
            try {
                request.setPage(page + 1);
                RestResponse<MALGetAssetsResponse> response = getAssetsClient.download(request);
                if (!response.isSuccess() ||
                        response.getResponse() == null ||
                        response.getResponse().getAssets() == null) {
                    continue;
                }
                transformPageIntoAssets(response.getResponse().getAssets(), includedAssetTypes);
            } catch (final Exception e) {
                final String message = String.format("Problem storing page [%s] to database with message [%s]", page, e.getMessage());
                final ReportsEntity reportsEntity = new ReportsEntity(ReportType.WARNING, getClass().getName(), null, message, ReportTo.BM, null, null, null);
                reportsRepository.save(reportsEntity);
                logger.error(message);
            }
        }
    }

    @Transactional
    public void transformPagesIntoNewAssets(final List<MALGetData> malGetDatas, Map<String, String> includedAssetTypes) {
        malGetDatas.forEach(malGetData -> {
            MALGetAsset malGetAsset = objectMapping(malGetData);
            if (includedAssetTypes.containsKey(malGetAsset.getAssetTypeId())
                    && (!malGetAsset.getAssetTypeId().equals(LOGO_ASSET_TYPE_ID) || isNotEPS(malGetAsset))) {
                setAssetEntityByFileFormat(malGetAsset);
            }
        });
    }

    protected MALGetAsset objectMapping(MALGetData malGetData) {
        MALGetAsset malGetAsset = new MALGetAsset();
        malGetAsset.setAssetId(malGetData.getAssetId());
        malGetAsset.setAssetTypeId(malGetData.getAssetTypeId());
        malGetAsset.setCollectionId(malGetData.getDefaultCollectionId());
        malGetAsset.setColorId(malGetData.getColorId());
        malGetAsset.setAssociation(malGetData.getAssociation());
        malGetAsset.setInstructions(malGetData.getInstructions());
        malGetAsset.setBrandId(malGetData.getBrandId());
        malGetAsset.setCaption(malGetData.getCaption());
        malGetAsset.setDateCreated(malGetData.getDateCreated());
        malGetAsset.setDescription(malGetData.getDescription());
        malGetAsset.setDestionationId(malGetData.getDestinationId());
        malGetAsset.setFilename(malGetData.getFilename());
        malGetAsset.setFileTypeId(malGetData.getFileTypeId());
        malGetAsset.setName(malGetData.getName());
        malGetAsset.setMetadata(malGetData.getMetadata());
        malGetAsset.setUsageDescription(malGetData.getUsageDescription());
        malGetAsset.setSubjectId(malGetData.getSubjectId());
        malGetAsset.setStatus(malGetData.getStatus());
        malGetAsset.setLastModified(malGetData.getLastModified());
        malGetAsset.setHws(true);
        if (malGetData.getStock() != null) {
            malGetAsset.setStock(malGetData.getStock());
        }
        malGetAsset.setPropertyId(malGetData.getPropertyId());
        if (malGetData.getRightsManaged() != null) {
            malGetAsset.setRightsManaged(malGetData.getRightsManaged());
        }
        if (malGetData.getLimitedRights() != null) {
            malGetAsset.setLimitedRights(malGetData.getLimitedRights());
        }
        if (malGetData.getZipped() != null) {
            malGetAsset.setZipped(malGetData.getZipped());
        }

        if (malGetData.getProperty() != null && malGetData.getProperty().getData() != null) {
            malGetAsset.setMarshaCode(malGetData.getProperty().getData().getMarshaCode());
        }

        if (malGetData.getFiles() != null && malGetData.getFiles().getData() != null && !malGetData.getFiles().getData().isEmpty()) {
            for (MALFilesData malFilesData : malGetData.getFiles().getData()) {
                switch (malFilesData.getType()) {
                    case "xl":
                        malGetAsset.setXlUrl(malFilesData.getS3Url());
                        break;
                    case "large":
                        malGetAsset.setLargeUrl(malFilesData.getS3Url());
                        break;
                    case "high":
                        malGetAsset.setHighUrl(malFilesData.getS3Url());
                        break;
                    case "medium":
                        malGetAsset.setMediumUrl(malFilesData.getS3Url());
                        break;
                    case "low":
                        malGetAsset.setThumbnailUrl(malFilesData.getS3Url());
                        break;
                    case "logo_jpg":
                        malGetAsset.setLogoJpgUrl(malFilesData.getS3Url());
                        break;
                    case "logo_png":
                        malGetAsset.setLogoPngUrl(malFilesData.getS3Url());
                        break;
                    case "offer":
                        malGetAsset.setOfferUrl(malFilesData.getS3Url());
                        break;
                    default:
                        break;
                }
            }
        }
        return malGetAsset;
    }


    @Transactional
    public void transformPageIntoAssets(final List<MALGetAsset> malGetAssets, Map<String, String> includedAssetTypes) {
        malGetAssets.forEach(malGetAsset -> {
            if (includedAssetTypes.containsKey(malGetAsset.getAssetTypeId())
                    && (!malGetAsset.getAssetTypeId().equals(LOGO_ASSET_TYPE_ID) || isNotEPS(malGetAsset))) {
                malGetAsset.setHws(false);
                setAssetEntityByFileFormat(malGetAsset);
            }
        });
    }

    private void setAssetEntityByFileFormat(MALGetAsset malGetAsset) {
        for (final String fileFormat : appConfig.getAppConfigData().getFileFormatsOrder()) {
            if (isMatchingFileFormat(fileFormat, "xl_url", malGetAsset.getXlUrl())) {
                final AssetEntity assetEntity = putIntoAssetMap(malGetAsset, MALAssetType.FILE);
                saveAssetEntity(assetEntity);
                return;
            }
            if (isMatchingFileFormat(fileFormat, "large_url", malGetAsset.getLargeUrl())) {
                final AssetEntity assetEntity = putIntoAssetMap(malGetAsset, MALAssetType.FILE);
                saveAssetEntity(assetEntity);
                return;
            }
            if (isMatchingFileFormat(fileFormat, "high_url", malGetAsset.getHighUrl())) {
                final AssetEntity assetEntity = putIntoAssetMap(malGetAsset, MALAssetType.FILE);
                saveAssetEntity(assetEntity);
                return;
            }
            if (isMatchingFileFormat(fileFormat, "medium_url", malGetAsset.getMediumUrl())) {
                final AssetEntity assetEntity = putIntoAssetMap(malGetAsset, MALAssetType.FILE);
                saveAssetEntity(assetEntity);
                return;
            }
            if (isMatchingFileFormat(fileFormat, "thumbnail_url", malGetAsset.getThumbnailUrl())) {
                final AssetEntity assetEntity = putIntoAssetMap(malGetAsset, MALAssetType.FILE);
                saveAssetEntity(assetEntity);
                return;
            }
            if (isMatchingFileFormat(fileFormat, "logo_jpg_url", malGetAsset.getLogoJpgUrl())) {
                final AssetEntity assetEntity = putIntoAssetMap(malGetAsset, MALAssetType.JPG_LOGO);
                saveAssetEntity(assetEntity);
                return;
            }
            if (isMatchingFileFormat(fileFormat, "logo_png_url", malGetAsset.getLogoPngUrl())) {
                final AssetEntity assetEntity = putIntoAssetMap(malGetAsset, MALAssetType.PNG_LOGO);
                saveAssetEntity(assetEntity);
                return;
            }
        }
    }

    private boolean isNotEPS(MALGetAsset malGetAsset) {
        return malGetAsset.getAssetTypeId().equals(LOGO_ASSET_TYPE_ID) &&
                (
                        malGetAsset.getXlUrl() != null && !malGetAsset.getXlUrl().toLowerCase().contains(EPS_FILE_FORMAT) ||
                                malGetAsset.getLargeUrl() != null && !malGetAsset.getLargeUrl().toLowerCase().contains(EPS_FILE_FORMAT) ||
                                malGetAsset.getHighUrl() != null && !malGetAsset.getHighUrl().toLowerCase().contains(EPS_FILE_FORMAT) ||
                                malGetAsset.getMediumUrl() != null && !malGetAsset.getMediumUrl().toLowerCase().contains(EPS_FILE_FORMAT) ||
                                malGetAsset.getThumbnailUrl() != null && !malGetAsset.getThumbnailUrl().toLowerCase().contains(EPS_FILE_FORMAT) ||
                                malGetAsset.getLogoJpgUrl() != null && !malGetAsset.getLogoJpgUrl().toLowerCase().contains(EPS_FILE_FORMAT) ||
                                malGetAsset.getLogoPngUrl() != null && !malGetAsset.getLogoPngUrl().toLowerCase().contains(EPS_FILE_FORMAT)
                );
    }

    private boolean isNotZip(String logoPngUrl) {
        return !logoPngUrl.toLowerCase().contains(ZIP_FILE_FORMAT);
    }

    private boolean isMatchingFileFormat(String fileFormat, String matchingFileFormat, String url) {
        return fileFormat.equals(matchingFileFormat) && StringUtils.isNotBlank(url) && isNotZip(url);
    }

    private void saveAssetEntity(AssetEntity assetEntity) {
        if (assetEntity != null) {
            bmAssetIdRepository.save(assetEntity.getBmAssetIdEntity());
            assetRepository.save(assetEntity);
        }
    }

    private AssetEntity putIntoAssetMap(final MALGetAsset malGetAsset, final MALAssetType assetType) {

        final String malMd5Hash;
        try {
            malMd5Hash = DigestUtils.md5Hex(objectMapper.writeValueAsString(malGetAsset));
        } catch (final Exception e) {
            return null;
        }
        final List<AssetEntity> dbAssetEntities = assetRepository.findAllByMalAssetIdAndAssetTypeAndUpdatedIsAfter(malGetAsset.getAssetId(), assetType, getMidnightBridgeLookInThePast());

        final AssetEntity assetEntity;
        if (!dbAssetEntities.isEmpty()) {
            for (final AssetEntity aE : dbAssetEntities) {
                if (StringUtils.isNotBlank(aE.getMalLastModified()) &&
                        aE.getMalLastModified().equals(malGetAsset.getLastModified()) &&
                        malMd5Hash.equals(aE.getMalMd5Hash())) {
                    return null;
                }
            }
            assetEntity = assetModelsToMALAssetEntityTransformer.fromMALGetAsset(malGetAsset, assetType);
            assetEntity.setBmAssetIdEntity(dbAssetEntities.get(0).getBmAssetIdEntity());
            assetEntity.setMalAssetOperation(MALAssetOperation.MAL_MODIFIED);
        } else {
            assetEntity = assetModelsToMALAssetEntityTransformer.fromMALGetAsset(malGetAsset, assetType);
            final BMAssetIdEntity bmAssetIdEntity = new BMAssetIdEntity("CREATING_" + malGetAsset.getAssetId());
            assetEntity.setBmAssetIdEntity(bmAssetIdEntity);
            assetEntity.setMalAssetOperation(MALAssetOperation.MAL_CREATED);
        }
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_OBSERVED);
        assetEntity.setMalMd5Hash(malMd5Hash);
        final AssetJsonedValuesEntity assetJsonedValuesEntity = assetEntity.getAssetJsonedValuesEntity();
        try {
            assetJsonedValuesEntity.setBmUploadMetadataArgumentJson(objectMapper.writeValueAsString(malToBMTransformer.transformToUploadMetadataArgument(malGetAsset)));
        } catch (final Exception e) {
            logger.error("Transforming of Metadata failed!", e);
        }

        if (StringUtils.isBlank(assetEntity.getUrl())) {
            final String message = String.format("Found asset with id [%s] with invalid download url", malGetAsset.getAssetId());
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), assetEntity.getMalAssetId(), message, ReportTo.MAL, (new Gson()).toJson(malGetAsset), null, null);
            reportsRepository.save(reportsEntity);
            assetEntity.setMalAssetOperation(MALAssetOperation.INVALID);
        }

        return assetEntity;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }
}
