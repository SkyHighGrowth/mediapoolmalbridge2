package MediaPoolMalBridge.service.BrandMaker.assetmetadata;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.assetmetadata.download.BMFireDownloadAssetsMetadataService;
import MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload.BMFireUploadAssetsMetadataService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BMExchangeAssetMetadataSchedulerService extends AbstractSchedulerService {

    private BMFireUploadAssetsMetadataService bmFireUploadAssetsMetadataService;

    private BMFireDownloadAssetsMetadataService bmFireDownloadAssetsMetadataService;

    public BMExchangeAssetMetadataSchedulerService(final BMFireUploadAssetsMetadataService bmFireUploadAssetsMetadataService,
                                                   final BMFireDownloadAssetsMetadataService bmFireDownloadAssetsMetadataService) {
        this.bmFireUploadAssetsMetadataService = bmFireUploadAssetsMetadataService;
        this.bmFireDownloadAssetsMetadataService = bmFireDownloadAssetsMetadataService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
    }

    @Override
    public void scheduled() {
        bmFireUploadAssetsMetadataService.start();
        bmFireDownloadAssetsMetadataService.start();
    }
}
