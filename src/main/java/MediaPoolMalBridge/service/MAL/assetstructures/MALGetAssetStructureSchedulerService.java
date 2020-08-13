package MediaPoolMalBridge.service.MAL.assetstructures;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetbrand.MALGetAssetBrandUniqueThreadService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetcollection.MALGetAssetCollectionUniqueThreadService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetcolor.MALGetAssetColorUniqueThreadService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetdestination.MALGetAssetDestinationUniqueThreadService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetfiletypes.MALGetAssetFileTypesUniqueThreadService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetsubject.MALGetAssetSubjectUniqueThreadService;
import MediaPoolMalBridge.service.MAL.assetstructures.assettype.MALGetAssetTypeUniqueThreadService;
import MediaPoolMalBridge.service.MAL.assetstructures.propertytypes.MALGetPropertyTypesUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MALGetAssetStructureSchedulerService extends AbstractSchedulerService {

    private final MALGetAssetBrandUniqueThreadService malGetAssetBrandUniqueThreadService;
    private final MALGetAssetCollectionUniqueThreadService malGetAssetCollectionUniqueThreadService;
    private final MALGetAssetColorUniqueThreadService malGetAssetColorUniqueThreadService;
    private final MALGetAssetDestinationUniqueThreadService malGetAssetDestinationUniqueThreadService;
    private final MALGetAssetFileTypesUniqueThreadService malGetAssetFileTypesUniqueThreadService;
    private final MALGetAssetSubjectUniqueThreadService malGetAssetSubjectUniqueThreadService;
    private final MALGetAssetTypeUniqueThreadService malGetAssetTypeUniqueThreadService;
    private final MALGetPropertyTypesUniqueThreadService malGetPropertyTypesUniqueThreadService;

    public MALGetAssetStructureSchedulerService(final MALGetAssetBrandUniqueThreadService malGetAssetBrandUniqueThreadService,
                                                final MALGetAssetCollectionUniqueThreadService malGetAssetCollectionUniqueThreadService,
                                                final MALGetAssetColorUniqueThreadService malGetAssetColorUniqueThreadService,
                                                final MALGetAssetDestinationUniqueThreadService malGetAssetDestinationUniqueThreadService,
                                                final MALGetAssetFileTypesUniqueThreadService malGetAssetFileTypesUniqueThreadService,
                                                final MALGetAssetSubjectUniqueThreadService malGetAssetSubjectUniqueThreadService,
                                                final MALGetAssetTypeUniqueThreadService malGetAssetTypeUniqueThreadService,
                                                final MALGetPropertyTypesUniqueThreadService malGetPropertyTypesUniqueThreadService) {
        this.malGetAssetBrandUniqueThreadService = malGetAssetBrandUniqueThreadService;
        this.malGetAssetCollectionUniqueThreadService = malGetAssetCollectionUniqueThreadService;
        this.malGetAssetColorUniqueThreadService = malGetAssetColorUniqueThreadService;
        this.malGetAssetDestinationUniqueThreadService = malGetAssetDestinationUniqueThreadService;
        this.malGetAssetFileTypesUniqueThreadService = malGetAssetFileTypesUniqueThreadService;
        this.malGetAssetSubjectUniqueThreadService = malGetAssetSubjectUniqueThreadService;
        this.malGetAssetTypeUniqueThreadService = malGetAssetTypeUniqueThreadService;
        this.malGetPropertyTypesUniqueThreadService = malGetPropertyTypesUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        scheduled();
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getMalAssetStructureCronExpression()));
    }

    @Override
    public void scheduled() {
        logger.info("Downloading asset brands form MAL started");
        malGetAssetBrandUniqueThreadService.start();
        logger.info("Downloading asset brands form MAL ended");
        logger.info("Downloading asset collections form MAL started");
        malGetAssetCollectionUniqueThreadService.start();
        logger.info("Downloading asset collections form MAL ended");
        logger.info("Downloading asset colors form MAL started");
        malGetAssetColorUniqueThreadService.start();
        logger.info("Downloading asset colors form MAL ended");
        logger.info("Downloading asset destinations form MAL started");
        malGetAssetDestinationUniqueThreadService.start();
        logger.info("Downloading asset destinations form MAL ended");
        logger.info("Downloading asset file types form MAL started");
        malGetAssetFileTypesUniqueThreadService.start();
        logger.info("Downloading asset file types  form MAL ended");
        logger.info("Downloading asset subjects form MAL started");
        malGetAssetSubjectUniqueThreadService.start();
        logger.info("Downloading asset subjects form MAL ended");
        logger.info("Downloading asset type form MAL started");
        malGetAssetTypeUniqueThreadService.start();
        logger.info("Downloading asset type form MAL ended");
        logger.info("Downloading property types form MAL started");
        malGetPropertyTypesUniqueThreadService.start();
        logger.info("Downloading property types form MAL ended");
    }
}
