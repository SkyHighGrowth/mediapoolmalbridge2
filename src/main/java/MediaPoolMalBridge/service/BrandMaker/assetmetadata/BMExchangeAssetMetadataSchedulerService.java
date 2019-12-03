package MediaPoolMalBridge.service.BrandMaker.assetmetadata;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.assetmetadata.download.BMFireDownloadAssetsMetadataUniqueThreadService;
import MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload.BMFireUploadAssetsMetadataUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service for metadata exchange between Mediapool server and MALToMediapoolBridge
 */
@Service
public class BMExchangeAssetMetadataSchedulerService extends AbstractSchedulerService {

    private BMFireUploadAssetsMetadataUniqueThreadService bmFireUploadAssetsMetadataUniqueThreadService;

    private BMFireDownloadAssetsMetadataUniqueThreadService bmFireDownloadAssetsMetadataUniqueThreadService;

    public BMExchangeAssetMetadataSchedulerService(final BMFireUploadAssetsMetadataUniqueThreadService bmFireUploadAssetsMetadataUniqueThreadService,
                                                   final BMFireDownloadAssetsMetadataUniqueThreadService bmFireDownloadAssetsMetadataUniqueThreadService) {
        this.bmFireUploadAssetsMetadataUniqueThreadService = bmFireUploadAssetsMetadataUniqueThreadService;
        this.bmFireDownloadAssetsMetadataUniqueThreadService = bmFireDownloadAssetsMetadataUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getBmExchangeSchedulerCronExpression()));
    }

    @Override
    public void scheduled() {
        bmFireUploadAssetsMetadataUniqueThreadService.start();
        bmFireDownloadAssetsMetadataUniqueThreadService.start();
    }
}
