package MediaPoolMalBridge.controller;

import MediaPoolMalBridge.model.BrandMaker.theme.BMThemes;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.model.MAL.kits.MALKits;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Profile("dev")
public class AppStatusController {

    private static Logger logger = LoggerFactory.getLogger(AppStatusController.class);

    private final MALKits malKits;
    private final BMThemes bmThemes;
    private final MALAssetStructures assetStructures;
    private TaskExecutorWrapper taskExecutorWrapper;

    public AppStatusController(final MALKits malKits,
                               final BMThemes bmThemes,
                               final MALAssetStructures assetStructures,
                               final TaskExecutorWrapper taskExecutorWrapper) {
        this.malKits = malKits;
        this.bmThemes = bmThemes;
        this.assetStructures = assetStructures;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    @GetMapping("/appStatus/mal/fileTypes")
    public Map<String, String> getFileTypes() {
        return assetStructures.getFileTypes();
    }

    @GetMapping("/appStatus/mal/kits")
    public MALKits getKits() {
        return malKits;
    }

    @GetMapping("/appStatus/bm/themes")
    public BMThemes getThemes() {
        return bmThemes;
    }

    @GetMapping("/appStatus/mal/brands")
    public Map<String, String> getBrands() {
        return assetStructures.getBrands();
    }

    @GetMapping("/appStatus/mal/collections")
    public Map<String, String> getCollections() {
        return assetStructures.getCollections();
    }

    @GetMapping("/appStatus/mal/colors")
    public Map<String, String> getColors() {
        return assetStructures.getColors();
    }

    @GetMapping("/appStatus/mal/destinations")
    public Map<String, String> getMalDestinations() {
        return assetStructures.getDestinations();
    }

    @GetMapping("/appStatus/mal/subjects")
    public Map<String, String> getSubjects() {
        return assetStructures.getSubjects();
    }

    @GetMapping("/appStatus/mal/assetTypes")
    public Map<String, String> getMalAssetTypes() {
        return assetStructures.getAssetTypes();
    }

    @GetMapping("/appStatus/app/propertyTypes")
    public Map<String, String> getPropertyTypes() {
        return assetStructures.getPropertyTypes();
    }

    @GetMapping("/appStatus/app/queueSize" )
    public int getQueueSize()
    {
        return taskExecutorWrapper.getQueueSize();
    }
}
