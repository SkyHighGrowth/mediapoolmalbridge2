package MediaPoolMalBridge.service.MAL.assets;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.assets.created.MALCollectCreatedAssetsSinceService;
import MediaPoolMalBridge.service.MAL.assets.deleted.MALCollectDeletedAssetsSinceService;
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

    private final MALCollectCreatedAssetsSinceService malCollectCreatedAssetsSinceService;

    private final MALCollectModifiedAssetSinceService malCollectModifiedAssetSinceService;

    private MALCollectDeletedAssetsSinceService malCollectDeletedAssetsSinceService;

    public MALAssetsSchedulerService(final MALCollectCreatedAssetsSinceService malCollectCreatedAssetsSinceService,
                                     final MALCollectModifiedAssetSinceService malCollectModifiedAssetSinceService,
                                     final MALCollectDeletedAssetsSinceService malCollectDeletedAssetsSinceService) {
        this.malCollectCreatedAssetsSinceService = malCollectCreatedAssetsSinceService;
        this.malCollectModifiedAssetSinceService = malCollectModifiedAssetSinceService;
        this.malCollectDeletedAssetsSinceService = malCollectDeletedAssetsSinceService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(Constants.CRON_MIDNIGHT_TRIGGGER_EXPRESSION));
    }

    @Override
    public void scheduled() {
        final String since = Instant.ofEpochMilli(System.currentTimeMillis())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .minusDays(200L)
                .format(DATE_TIME_FORMATTER);

        malCollectCreatedAssetsSinceService.setSince( since );
        malCollectCreatedAssetsSinceService.start();
        malCollectModifiedAssetSinceService.setSince( since );
        malCollectModifiedAssetSinceService.start();
        malCollectDeletedAssetsSinceService.setSince( since );
        malCollectDeletedAssetsSinceService.start();
    }
}
