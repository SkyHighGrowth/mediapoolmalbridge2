package MediaPoolMalBridge.service.MAL.assets;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.config.MalIncludedAssetTypes;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetIdEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetJsonedValuesEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetIdRepository;
import MediaPoolMalBridge.persistence.transformer.MAL.MALAssetModelsToMALAssetEntityTransformer;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
import MediaPoolMalBridge.service.MAL.assets.transformer.MALToBMTransformer;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class that collects common fields and methods of services that collects data from MAL server
 */
public abstract class AbstractMALAssetsUniqueThreadService extends AbstractMALUniqueThreadService {

    @Autowired
    private MALGetAssetsClient getAssetsClient;

    @Autowired
    private MALAssetModelsToMALAssetEntityTransformer assetModelsToMALAssetEntityTransformer;

    @Autowired
    private MALToBMTransformer malToBMTransformer;

    @Autowired
    private BMAssetIdRepository bmAssetIdRepository;

    @Autowired
    private MalIncludedAssetTypes includedAssetTypes;

    private String since;

    protected void downloadAssets(final MALGetAssetsRequest request) {
        request.setPage(1);
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

    private void transformPagesIntoAssets(final MALGetAssetsRequest request, final int totalPages) {
        for (int page = 0; page < totalPages; ++page) {
            try {
                request.setPage(page + 1);
                RestResponse<MALGetAssetsResponse> response = getAssetsClient.download(request);
                if (!response.isSuccess() ||
                        response.getResponse() == null ||
                        response.getResponse().getAssets() == null) {
                    continue;
                }

                transformPageIntoAssets(response.getResponse().getAssets());
            } catch (final Exception e) {
                final String message = String.format("Problem storing page [%s] to database with message [%s]", page, e.getMessage());
                final ReportsEntity reportsEntity = new ReportsEntity(ReportType.WARNING, getClass().getName(), null, message, ReportTo.BM, null, null, null);
                reportsRepository.save(reportsEntity);
                logger.error(message);
            }
        }
    }

    @Transactional
    public void transformPageIntoAssets(final List<MALGetAsset> malGetAssets) {
        malGetAssets.forEach(malGetAsset -> {
            if (includedAssetTypes.isIncludedAssetType(malGetAsset.getAssetTypeId())
                    && StringUtils.isNotEmpty(malGetAsset.getBrandId()) && malPriorities.contains(malGetAsset.getBrandId())) {
                setAssetEntityByFileFormat(malGetAsset);
            }
        });
    }

    private void setAssetEntityByFileFormat(MALGetAsset malGetAsset) {
        for (final String fileFormat : appConfig.getFileFormatsOrder()) {
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

    private boolean isMatchingFileFormat(String fileFormat, String matchingFileFormat, String xlUrl) {
        return fileFormat.equals(matchingFileFormat) && StringUtils.isNotBlank(xlUrl);
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
