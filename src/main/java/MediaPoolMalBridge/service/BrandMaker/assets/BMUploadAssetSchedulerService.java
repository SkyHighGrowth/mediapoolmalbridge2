package MediaPoolMalBridge.service.BrandMaker.assets;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.assets.create.BMFireCreateAssetsService;
import MediaPoolMalBridge.service.BrandMaker.assets.delete.BMFireDeleteAssetsService;
import MediaPoolMalBridge.service.BrandMaker.assets.upload.BMFireUploadAssetsService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BMUploadAssetSchedulerService extends AbstractSchedulerService {

    private final BMFireCreateAssetsService bmFireCreateAssetsService;

    private final BMFireUploadAssetsService bmFireUploadAssetsService;

    private final BMFireDeleteAssetsService bmFireDeleteAssetsService;

    public BMUploadAssetSchedulerService(final BMFireCreateAssetsService bmFireCreateAssetsService,
                                         final BMFireUploadAssetsService bmFireUploadAssetsService,
                                         final BMFireDeleteAssetsService bmFireDeleteAssetsService) {
        this.bmFireCreateAssetsService = bmFireCreateAssetsService;
        this.bmFireUploadAssetsService = bmFireUploadAssetsService;
        this.bmFireDeleteAssetsService = bmFireDeleteAssetsService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
    }

    @Override
    public void scheduled() {
        create();
        update();
        delete();
    }

    public void create() {
        bmFireCreateAssetsService.start();
    }

    public void update() {
        bmFireUploadAssetsService.start();
    }

    public void delete() {
        bmFireDeleteAssetsService.start();
    }

}
