package MediaPoolMalBridge.service.BrandMaker.assetmetadata;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.assetmetadata.download.BMDownloadAssetMetadataService;
import MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload.BMUploadAssetMetadataService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BMExchangeAssetMetadataSchedulerService extends AbstractSchedulerService {

    private BMUploadAssetMetadataService bmUploadAssetMetadataService;

    private BMDownloadAssetMetadataService bmDownloadAssetMetadataService;

    public BMExchangeAssetMetadataSchedulerService(final BMUploadAssetMetadataService bmUploadAssetMetadataService,
                                                   final BMDownloadAssetMetadataService bmDownloadAssetMetadataService) {
        this.bmUploadAssetMetadataService = bmUploadAssetMetadataService;
        this.bmDownloadAssetMetadataService = bmDownloadAssetMetadataService;
    }

    @PostConstruct
    public void scheduleUpload() {
        if (isRunScheduler()) {
            taskSchedulerWrapper.getTaskScheduler().schedule(this::exchangeMetadata, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
        }
    }

    public void exchangeMetadata() {
        bmUploadAssetMetadataService.upload();
        bmDownloadAssetMetadataService.download();
    }
}
