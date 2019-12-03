package MediaPoolMalBridge.service.BrandMaker.assets;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.BrandMaker.assets.create.BMFireCreateAssetsUniqueThreadService;
import MediaPoolMalBridge.service.BrandMaker.assets.delete.BMFireDeleteAssetsUniqueThreadService;
import MediaPoolMalBridge.service.BrandMaker.assets.getassetid.BMFireGetAssetIdFromHashUniqueThreadService;
import MediaPoolMalBridge.service.BrandMaker.assets.upload.BMFireUploadAssetsUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service for creating, modifying, deleting and obtaining asset id from Mediapool server
 */
@Service
public class BMUploadAssetSchedulerService extends AbstractSchedulerService {

    private final BMFireCreateAssetsUniqueThreadService bmFireCreateAssetsUniqueThreadService;

    private final BMFireUploadAssetsUniqueThreadService bmFireUploadAssetsUniqueThreadService;

    private final BMFireGetAssetIdFromHashUniqueThreadService bmFireGetAssetIdFromHashUniqueThreadService;

    private final BMFireDeleteAssetsUniqueThreadService bmFireDeleteAssetsUniqueThreadService;

    public BMUploadAssetSchedulerService(final BMFireCreateAssetsUniqueThreadService bmFireCreateAssetsUniqueThreadService,
                                         final BMFireUploadAssetsUniqueThreadService bmFireUploadAssetsUniqueThreadService,
                                         final BMFireGetAssetIdFromHashUniqueThreadService bmFireGetAssetIdFromHashUniqueThreadService,
                                         final BMFireDeleteAssetsUniqueThreadService bmFireDeleteAssetsUniqueThreadService) {
        this.bmFireCreateAssetsUniqueThreadService = bmFireCreateAssetsUniqueThreadService;
        this.bmFireUploadAssetsUniqueThreadService = bmFireUploadAssetsUniqueThreadService;
        this.bmFireGetAssetIdFromHashUniqueThreadService = bmFireGetAssetIdFromHashUniqueThreadService;
        this.bmFireDeleteAssetsUniqueThreadService = bmFireDeleteAssetsUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getBmUploadSchedulerCronExpression()));
    }

    @Override
    public void scheduled() {
        create();
        getAssetId();
        update();
        delete();
    }

    public void create() {
        bmFireCreateAssetsUniqueThreadService.start();
    }

    public void update() {
        bmFireUploadAssetsUniqueThreadService.start();
    }

    public void getAssetId() { bmFireGetAssetIdFromHashUniqueThreadService.start(); }

    public void delete() {
        bmFireDeleteAssetsUniqueThreadService.start();
    }

}
