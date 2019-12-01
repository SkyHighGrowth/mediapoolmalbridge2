package MediaPoolMalBridge.service.MAL.assets.download.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import MediaPoolMalBridge.service.MAL.assets.download.MALDownloadAssetService;
import MediaPoolMalBridge.service.MAL.assets.download.MALFireDownloadAssetsUniqueThreadService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@Profile("dev")
public class MALDownloadAssetController {

    private static Logger logger = LoggerFactory.getLogger(MALDownloadAssetController.class);
    private final MALDownloadAssetService malDownloadAssetService;
    private final MALFireDownloadAssetsUniqueThreadService malFireDownloadAssetsUniqueThreadService;
    private final TaskExecutorWrapper taskExecutorWrapper;
    private final MALGetAssetsDev malGetAssetsDev;
    private final AssetRepository assetRepository;

    public MALDownloadAssetController(final MALDownloadAssetService malDownloadAssetService,
                                      final MALFireDownloadAssetsUniqueThreadService malFireDownloadAssetsUniqueThreadService,
                                      final TaskExecutorWrapper taskExecutorWrapper,
                                      final MALGetAssetsDev malGetAssetsDev,
                                      final AssetRepository assetRepository) {
        this.malDownloadAssetService = malDownloadAssetService;
        this.malFireDownloadAssetsUniqueThreadService = malFireDownloadAssetsUniqueThreadService;
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malGetAssetsDev = malGetAssetsDev;
        this.assetRepository = assetRepository;
    }

    @PostMapping(value = "/service/mal/downloadAssets", consumes = "application/json")
    public String getAssets(@RequestBody final MALGetAssetsRequest request) {
        malGetAssetsDev.start(request);
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(
                    TransferringAssetStatus.ASSET_OBSERVED, getTodayMidnight(), PageRequest.of(page, 1000));
            logger.error( "page {} num of assets {}", page, assetEntities.getNumberOfElements());
            condition = assetEntities.hasNext();
            assetEntities.forEach(assetEntity -> {
                if (taskExecutorWrapper.getQueueSize() < 9000) {
                    logger.error( "hitted it" );
                    if(MALAssetOperation.MAL_CREATED.equals(assetEntity.getMalAssetOperation())) {
                        taskExecutorWrapper.getTaskExecutor().execute(() -> malDownloadAssetService.start(assetEntity));
                    }
                } else {
                    logger.error( "missed it" );
                }
            });
        }
        return "hello";
    }

    @GetMapping( "/service/mal/firedownload" )
    public void fireDownload()
    {
        malFireDownloadAssetsUniqueThreadService.start();
    }

    protected LocalDateTime getTodayMidnight() {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
    }
}
