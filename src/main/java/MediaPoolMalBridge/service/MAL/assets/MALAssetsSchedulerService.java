package MediaPoolMalBridge.service.MAL.assets;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.assets.created.MALCollectCreatedAssetsSinceService;
import MediaPoolMalBridge.service.MAL.assets.deleted.MALCollectDeletedAssetsSinceService;
import MediaPoolMalBridge.service.MAL.assets.download.MALFireDownloadAssetsService;
import MediaPoolMalBridge.service.MAL.assets.modified.MALCollectModifiedAssetSinceService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class MALAssetsSchedulerService extends AbstractSchedulerService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final MALFireDownloadAssetsService malFireDownloadAssetsService;

    private final MALCollectCreatedAssetsSinceService malCollectCreatedAssetsSinceService;

    private final MALCollectModifiedAssetSinceService malCollectModifiedAssetSinceService;

    private MALCollectDeletedAssetsSinceService malCollectDeletedAssetsSinceService;

    public MALAssetsSchedulerService(final MALFireDownloadAssetsService malFireDownloadAssetsService,
                                     final MALCollectCreatedAssetsSinceService malCollectCreatedAssetsSinceService,
                                     final MALCollectModifiedAssetSinceService malCollectModifiedAssetSinceService,
                                     final MALCollectDeletedAssetsSinceService malCollectDeletedAssetsSinceService) {
        this.malFireDownloadAssetsService = malFireDownloadAssetsService;
        this.malCollectCreatedAssetsSinceService = malCollectCreatedAssetsSinceService;
        this.malCollectModifiedAssetSinceService = malCollectModifiedAssetSinceService;
        this.malCollectDeletedAssetsSinceService = malCollectDeletedAssetsSinceService;
    }

    @PostConstruct
    public void scheduleUpdates() {
        if (isRunScheduler()) {
            taskSchedulerWrapper.getTaskScheduler().schedule(this::update, new CronTrigger(Constants.CRON_DAILY_TRIGGGER_EXPRESSION));
            taskSchedulerWrapper.getTaskScheduler().schedule(this::downloadAssets, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
        }
    }

    public void update() {
        final String since = Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .format(DATE_TIME_FORMATTER);
        logger.info("Executing assets update task, modified since {} UTC", since);
        malCollectCreatedAssetsSinceService.downloadCreatedAssets(since);
        malCollectModifiedAssetSinceService.downloadModifiedAssets(since);
        malCollectDeletedAssetsSinceService.downloadUnavailableAssets(since);
    }

    public void downloadAssets() {
        malFireDownloadAssetsService.downloadAssets();
    }
}
