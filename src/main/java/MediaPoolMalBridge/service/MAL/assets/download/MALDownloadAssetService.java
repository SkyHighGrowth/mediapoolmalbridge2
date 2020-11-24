package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.clients.BrandMaker.assetid.client.BMGetAssetIdFromHashClient;
import MediaPoolMalBridge.clients.MAL.download.client.MALDownloadAssetClient;
import MediaPoolMalBridge.clients.MAL.download.client.model.MALDownloadAssetResponse;
import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.MAL.AbstractMALNonUniqueThreadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Service that triggers download of asset from MAL server
 */
@Service
public class MALDownloadAssetService extends AbstractMALNonUniqueThreadService<AssetEntity> {

    private final MALDownloadAssetClient malDownloadAssetClient;

    private final UploadedFileRepository uploadedFileRepository;

    private final BMGetAssetIdFromHashClient bmGetAssetIdFromHashClient;

    public MALDownloadAssetService(final MALDownloadAssetClient malDownloadAssetClient,
                                   final UploadedFileRepository uploadedFileRepository,
                                   final BMGetAssetIdFromHashClient bmGetAssetIdFromHashClient) {
        this.malDownloadAssetClient = malDownloadAssetClient;
        this.uploadedFileRepository = uploadedFileRepository;
        this.bmGetAssetIdFromHashClient = bmGetAssetIdFromHashClient;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        long size = getCurrentDownloadFolderSize();
        long downloadFolderSizeLimit = getDownloadFolderSizeLimitInBytes();
        if (size < downloadFolderSizeLimit) {
            //check if the media file already exist in BM
            String mediaGuidByHash = bmGetAssetIdFromHashClient.getMediaPoolPort().getMediaGuidByHash(assetEntity.getBmMd5Hash());
            if (StringUtils.isEmpty(mediaGuidByHash)) {
                try {
                    final MALDownloadAssetResponse response = malDownloadAssetClient.download(decode(assetEntity.getUrl()), assetEntity.getFileNameOnDisc());
                    if (response.isSuccess()) {
                        onSuccess(assetEntity);
                    } else {
                        onFailure(assetEntity, response, null);
                    }
                } catch (final Exception e) {
                    onFailure(assetEntity, null, e);
                }
                assetRepository.save(assetEntity);
            }
        }
    }

    private long getCurrentDownloadFolderSize() {
        File tempDir = new File(appConfig.getTempDir());
        long size = 0;
        try {
            size = Files.walk(tempDir.toPath())
                    .filter(p -> p.toFile().isFile())
                    .mapToLong(p -> p.toFile().length())
                    .sum();
        } catch (IOException e) {
            logger.error(String.format("Could not calculate %s size", tempDir.getName()), e);
        }
        return size;
    }

    private void onSuccess(final AssetEntity assetEntity) {
        assetEntity.setMalStatesRepetitions(0);
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_DOWNLOADED);
        assetRepository.save(assetEntity);
    }

    private long getDownloadFolderSizeLimitInBytes() {
        //For Windows server change this to 1024
        return appConfig.getDownloadFolderSizeLimit() * 1000 * 1000 * 1000;
    }

    @Transactional
    protected void onFailure(final AssetEntity assetEntity, final MALAbstractResponse malAbstractResponse, final Exception e) {
        if (!isGateOpen(assetEntity, "asset download", malAbstractResponse, e)) {
            return;
        }
        uploadedFileRepository.save(new UploadedFileEntity(assetEntity.getFileNameOnDisc()));
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_ONBOARDED);
        assetRepository.save(assetEntity);
        final String message;
        if (e == null) {
            message = String.format("Problem downloading asset with id [%s] and url [%s], with message [%s]", assetEntity.getMalAssetId(), assetEntity.getUrl(), malAbstractResponse.getMessage());
        } else {
            message = String.format("Problem downloading asset with id [%s] and url [%s], with message [%s]", assetEntity.getMalAssetId(), assetEntity.getUrl(), e.getMessage());
        }
        final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), assetEntity.getMalAssetId(), message, ReportTo.NONE, GSON.toJson(assetEntity), null, null);
        reportsRepository.save(reportsEntity);
    }

    @Override
    @Transactional
    protected boolean isGateOpen(final AssetEntity assetEntity, final String serviceDescription, final MALAbstractResponse malAbstractResponse, final Exception e) {
        if (super.isGateOpen(assetEntity, serviceDescription, malAbstractResponse, e)) {
            return true;
        }
        uploadedFileRepository.save(new UploadedFileEntity(assetEntity.getFileNameOnDisc()));
        return false;
    }

    private String decode(final String encoded)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
    }
}
