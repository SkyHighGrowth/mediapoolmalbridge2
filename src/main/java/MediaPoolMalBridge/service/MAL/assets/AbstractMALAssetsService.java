package MediaPoolMalBridge.service.MAL.assets;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class AbstractMALAssetsService extends AbstractMALService {

    @Autowired
    private MALGetAssetsClient getAssetsClient;

    protected void downloadAssets(final MALGetAssetsRequest request, final String since) {
        request.setPage(1);
        RestResponse<MALGetAssetsResponse> response = getAssetsClient.download(request);
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                StringUtils.isBlank(response.getResponse().getTotalPages()) ||
                response.getResponse().getAssets() == null) {
            return;
        }

        final int totalPages;
        try {
            totalPages = Integer.parseInt(response.getResponse().getTotalPages());
        } catch (final Exception e) {
            final String message = String.format("Can not parse totalPages of created asset since [%s], with httpStatus [%s], and responseBody [%s]",
                    since,
                    response.getHttpStatus(),
                    GSON.toJson(response.getResponse()));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        transformPagesIntoAssets(request, totalPages);
    }

    public void transformPagesIntoAssets(final MALGetAssetsRequest request, final int totalPages) {
        for (int page = 0; page < totalPages; ++page) {
            request.setPage(page + 1);
            RestResponse<MALGetAssetsResponse> response = getAssetsClient.download(request);
            if (!response.isSuccess() ||
                    response.getResponse() == null ||
                    response.getResponse().getAssets() == null) {
                continue;
            }
            response.getResponse()
                    .getAssets()
                    .forEach(malGetAsset -> {
                        if (StringUtils.isNotBlank(malGetAsset.getThumbnailUrl()) ||
                                StringUtils.isNotBlank(malGetAsset.getMediumUrl()) ||
                                StringUtils.isNotBlank(malGetAsset.getLargeUrl()) ||
                                StringUtils.isNotBlank(malGetAsset.getXlUrl())) {
                            putIntoMALAssetMap(malGetAsset, MALAssetType.FILE);
                        }

                        if (StringUtils.isNotBlank(malGetAsset.getLogoJpgUrl())) {
                            putIntoMALAssetMap(malGetAsset, MALAssetType.JPG_LOGO);
                        }

                        if (StringUtils.isNotBlank(malGetAsset.getLogoPngUrl())) {
                            putIntoMALAssetMap(malGetAsset, MALAssetType.PNG_LOGO);
                        }
                    });
        }
    }

    private void putIntoMALAssetMap(final MALGetAsset malGetAsset, final MALAssetType malAssetType) {
        final Optional<MALAssetEntity> optionalMALAssetEntity = malAssetRepository.findByAssetIdAndAssetType( malGetAsset.getAssetId(), malAssetType );
        final MALAssetEntity malAssetEntity;
        if (optionalMALAssetEntity.isPresent()) {
            malAssetEntity = optionalMALAssetEntity.get();
            malAssetEntity.setTransferringAssetStatus(TransferringAssetStatus.MAL_UPDATED);
            malAssetEntity.setBmAssetId(malAssetEntity.getBmAssetId());
        } else {
            malAssetEntity = MALAssetEntity.fromMALGetAsset(malGetAsset, malAssetType);
            malAssetEntity.setTransferringAssetStatus(TransferringAssetStatus.MAL_CREATED);
            malAssetEntity.setBmAssetId("CREATING_" + malAssetEntity.getAssetId());
        }
        malAssetRepository.save( malAssetEntity );
    }
}
