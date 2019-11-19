package MediaPoolMalBridge.controller;

import MediaPoolMalBridge.model.BrandMaker.theme.BMThemes;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.model.MAL.kits.MALKits;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Profile("dev")
public class AppStatusController {

    private final MALKits malKits;
    private final BMThemes bmThemes;
    private final MALAssetStructures malAssetStructures;

    public AppStatusController(final MALKits malKits,
                               final BMThemes bmThemes,
                               final MALAssetStructures malAssetStructures ) {
        this.malKits = malKits;
        this.bmThemes = bmThemes;
        this.malAssetStructures = malAssetStructures;
    }

    @GetMapping("/appStatus/mal/fileTypes")
    public Map<String, String> getFileTypes() {
        return malAssetStructures.getFileTypes();
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
        return malAssetStructures.getBrands();
    }

    @GetMapping("/appStatus/mal/collections")
    public Map<String, String> getCollections() {
        return malAssetStructures.getCollections();
    }

    @GetMapping("/appStatus/mal/colors")
    public Map<String, String> getColors() {
        return malAssetStructures.getColors();
    }

    @GetMapping("/appStatus/mal/destinations")
    public Map<String, String> getMalDestinations() {
        return malAssetStructures.getDestinations();
    }

    @GetMapping("/appStatus/mal/subjects")
    public Map<String, String> getSubjects() {
        return malAssetStructures.getSubjects();
    }

    @GetMapping("/appStatus/mal/assetTypes")
    public Map<String, String> getMalAssetTypes() {
        return malAssetStructures.getAssetTypes();
    }

    @GetMapping("/appStatus/app/propertyTypes")
    public Map<String, String> getPropertyTypes() {
        return malAssetStructures.getPropertyTypes();
    }
}
