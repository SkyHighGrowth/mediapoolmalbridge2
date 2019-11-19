package MediaPoolMalBridge.service.MAL.assetstructures;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetbrand.MALGetAssetBrandService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetcollection.MALGetAssetCollectionService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetcolor.MALGetAssetColorService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetdestination.MALGetAssetDestinationService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetfiletypes.MALGetAssetFileTypesService;
import MediaPoolMalBridge.service.MAL.assetstructures.assetsubject.MALGetAssetSubjectService;
import MediaPoolMalBridge.service.MAL.assetstructures.assettype.MALGetAssetTypeService;
import MediaPoolMalBridge.service.MAL.assetstructures.propertytypes.MALGetPropertyTypesService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MALGetAssetStructureSchedulerService extends AbstractSchedulerService {

    private final MALGetAssetBrandService malGetAssetBrandService;
    private final MALGetAssetCollectionService malGetAssetCollectionService;
    private final MALGetAssetColorService malGetAssetColorService;
    private final MALGetAssetDestinationService malGetAssetDestinationService;
    private final MALGetAssetFileTypesService malGetAssetFileTypesService;
    private final MALGetAssetSubjectService malGetAssetSubjectService;
    private final MALGetAssetTypeService malGetAssetTypeService;
    private final MALGetPropertyTypesService malGetPropertyTypesService;

    public MALGetAssetStructureSchedulerService(final MALGetAssetBrandService malGetAssetBrandService,
                                                final MALGetAssetCollectionService malGetAssetCollectionService,
                                                final MALGetAssetColorService malGetAssetColorService,
                                                final MALGetAssetDestinationService malGetAssetDestinationService,
                                                final MALGetAssetFileTypesService malGetAssetFileTypesService,
                                                final MALGetAssetSubjectService malGetAssetSubjectService,
                                                final MALGetAssetTypeService malGetAssetTypeService,
                                                final MALGetPropertyTypesService malGetPropertyTypesService) {
        this.malGetAssetBrandService = malGetAssetBrandService;
        this.malGetAssetCollectionService = malGetAssetCollectionService;
        this.malGetAssetColorService = malGetAssetColorService;
        this.malGetAssetDestinationService = malGetAssetDestinationService;
        this.malGetAssetFileTypesService = malGetAssetFileTypesService;
        this.malGetAssetSubjectService = malGetAssetSubjectService;
        this.malGetAssetTypeService = malGetAssetTypeService;
        this.malGetPropertyTypesService = malGetPropertyTypesService;
    }

    @PostConstruct
    public void assetStructuresJob() {
        getAssetStructures();
        if (isRunScheduler()) {
            taskSchedulerWrapper.getTaskScheduler().schedule(this::getAssetStructures, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
        }
    }

    public void getAssetStructures() {
        malGetAssetBrandService.download();
        malGetAssetCollectionService.download();
        malGetAssetColorService.download();
        malGetAssetDestinationService.download();
        malGetAssetFileTypesService.download();
        malGetAssetSubjectService.download();
        malGetAssetTypeService.download();
        malGetPropertyTypesService.download();
    }
}
