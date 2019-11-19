package MediaPoolMalBridge.service.BrandMaker.assets;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.assets.create.BMCreateAssetService;
import MediaPoolMalBridge.service.BrandMaker.assets.delete.BMDeleteAssetService;
import MediaPoolMalBridge.service.BrandMaker.assets.upload.BMUploadAssetService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BMUploadAssetSchedulerService extends AbstractSchedulerService {

    private final BMCreateAssetService bmCreateAssetService;

    private final BMUploadAssetService bmUploadAssetService;

    private final BMDeleteAssetService bmDeleteAssetService;

    public BMUploadAssetSchedulerService(final BMCreateAssetService bmCreateAssetService,
                                         final BMUploadAssetService bmUploadAssetService,
                                         final BMDeleteAssetService bmDeleteAssetService) {
        this.bmCreateAssetService = bmCreateAssetService;
        this.bmUploadAssetService = bmUploadAssetService;
        this.bmDeleteAssetService = bmDeleteAssetService;
    }

    @PostConstruct
    public void initializeUpload() {
        if (isRunScheduler()) {
            taskSchedulerWrapper.getTaskScheduler().schedule(this::doOnAssets, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
        }
    }

    public void doOnAssets() {
        create();
        update();
        delete();
    }

    public void create() {
        bmCreateAssetService.createAssets();
    }

    public void update() {
        bmUploadAssetService.uploadAssets();
    }

    public void delete() {
        bmDeleteAssetService.deleteAssets();
    }

}
