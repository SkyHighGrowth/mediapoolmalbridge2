package com.brandmaker.mediapoolmalbridge.service.bridge.database.assetresolver;

import com.brandmaker.mediapoolmalbridge.config.AppConfigData;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.UploadedFileEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.UploadedFileRepository;
import com.brandmaker.mediapoolmalbridge.service.bridge.AbstractBridgeUniqueThreadService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BridgeDatabaseAssetResolverUniqueThreadService extends AbstractBridgeUniqueThreadService {

    private final Gson gson;

    private final UploadedFileRepository uploadedFileRepository;

    public BridgeDatabaseAssetResolverUniqueThreadService(final UploadedFileRepository uploadedFileRepository, @Qualifier("GsonSerializer") Gson gson) {
        this.uploadedFileRepository = uploadedFileRepository;
        this.gson = gson;
    }

    @Override
    protected void run() {
        for (int page = 0; true; ++page) {
            AppConfigData appConfigData = appConfig.getAppConfigData();
            final List<AssetEntity> assetEntities = assetRepository.findAllByUpdatedIsAfterAndUpdatedIsBeforeAndTransferringAssetStatusIsNotAndTransferringAssetStatusIsNot(
                    getMidnightBridgeLookInThePast().minusDays(appConfigData.getBridgeResolverWindow()), getMidnightBridgeLookInThePast(), TransferringAssetStatus.DONE, TransferringAssetStatus.ERROR, PageRequest.of(0, appConfig.getDatabasePageSize()));
            if (assetEntities.isEmpty() || page > 1000) {
                break;
            }
            resolvePage(assetEntities);
        }
    }

    @Transactional
    public void resolvePage(final List<AssetEntity> assetEntities) {
        assetEntities.forEach(assetEntity -> {
            final String message = String.format("Asset with id [%s] transfer ended in status [%s] and total transfer has not finished successfully", assetEntity.getMalAssetId(), assetEntity.getTransferringAssetStatus().name());
            final ReportsEntity reportMal = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, gson.toJson(assetEntity), null, null);
            reportsRepository.save(reportMal);
            final ReportsEntity reportBM = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, gson.toJson(assetEntity), null, null);
            reportsRepository.save(reportBM);
            if (StringUtils.isNotBlank(assetEntity.getFileNameOnDisc()) &&
                    !TransferringAssetStatus.ASSET_OBSERVED.equals(assetEntity.getTransferringAssetStatus())) {
                uploadedFileRepository.save(new UploadedFileEntity(assetEntity.getFileNameOnDisc()));
            }
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ERROR);
            assetRepository.save(assetEntity);
        });
    }
}
