package MediaPoolMalBridge.service.MAL.assets.download.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
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
    private final MALAssetRepository malAssetRepository;

    public MALDownloadAssetController(final MALDownloadAssetService malDownloadAssetService,
                                      final MALFireDownloadAssetsUniqueThreadService malFireDownloadAssetsUniqueThreadService,
                                      final TaskExecutorWrapper taskExecutorWrapper,
                                      final MALGetAssetsDev malGetAssetsDev,
                                      final MALAssetRepository malAssetRepository) {
        this.malDownloadAssetService = malDownloadAssetService;
        this.malFireDownloadAssetsUniqueThreadService = malFireDownloadAssetsUniqueThreadService;
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malGetAssetsDev = malGetAssetsDev;
        this.malAssetRepository = malAssetRepository;
    }

    @PostMapping(value = "/service/mal/downloadAssets", consumes = "application/json")
    public String getAssets(@RequestBody final MALGetAssetsRequest request) {
        malGetAssetsDev.start(request);
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<MALAssetEntity> malAssetEntities = malAssetRepository.findAllByTransferringMALConnectionAssetStatusOrTransferringMALConnectionAssetStatusAndUpdatedIsAfter(
                    TransferringMALConnectionAssetStatus.OBSERVED, TransferringMALConnectionAssetStatus.DOWNLOADING, getTodayMidnight(), PageRequest.of(page, 1000));
            logger.error( "page {} num of assets {}", page, malAssetEntities.getSize());
            condition = malAssetEntities.hasNext();
            malAssetEntities.forEach(malAssetEntity -> {
                if (taskExecutorWrapper.getQueueSize() < 9000) {
                    logger.error( "hitted it" );
                    taskExecutorWrapper.getTaskExecutor().execute(() -> malDownloadAssetService.start(malAssetEntity));
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
