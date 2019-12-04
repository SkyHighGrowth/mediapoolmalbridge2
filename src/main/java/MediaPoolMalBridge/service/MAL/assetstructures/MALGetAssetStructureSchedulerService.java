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
        malGetAssetBrandUniqueThreadService.start();
        malGetAssetCollectionUniqueThreadService.start();
        malGetAssetColorUniqueThreadService.start();
        malGetAssetDestinationUniqueThreadService.start();
        malGetAssetFileTypesUniqueThreadService.start();
        malGetAssetSubjectUniqueThreadService.start();
        malGetAssetTypeUniqueThreadService.start();
        malGetPropertyTypesUniqueThreadService.start();
    }
}
