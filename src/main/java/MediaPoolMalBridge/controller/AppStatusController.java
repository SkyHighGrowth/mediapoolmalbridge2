package MediaPoolMalBridge.controller;

import MediaPoolMalBridge.model.BrandMaker.BMAssetMap;
import MediaPoolMalBridge.model.BrandMaker.theme.BMThemes;
import MediaPoolMalBridge.model.MAL.filetype.MALFileTypes;
import MediaPoolMalBridge.model.MAL.MALAssetMap;
import MediaPoolMalBridge.model.MAL.kits.MALKits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile( "dev" )
public class AppStatusController {

    private static Logger logger = LoggerFactory.getLogger(AppStatusController.class);

    private final BMAssetMap bmAssetMap;

    private final MALAssetMap malAssetMap;

    private final MALFileTypes malFileTypes;

    private final MALKits malKits;

    private final BMThemes bmThemes;

    public AppStatusController(final BMAssetMap bmAssetMap,
                               final MALAssetMap malAssetMap,
                               final MALFileTypes malFileTypes,
                               final MALKits malKits,
                               final BMThemes bmThemes )
    {
        this.malAssetMap = malAssetMap;
        this.bmAssetMap = bmAssetMap;
        this.malFileTypes = malFileTypes;
        this.malKits = malKits;
        this.bmThemes = bmThemes;
    }

    @GetMapping("/appStatus/mal/assets")
    public MALAssetMap getMalAssetMap()
    {
        return malAssetMap;
    }

    @GetMapping("/appStatus/bm/assets")
    public BMAssetMap getBMAssetMap()
    {
        return bmAssetMap;
    }

    @GetMapping("/appStatus/mal/fileType")
    public MALFileTypes getFileTypes() { return malFileTypes; }

    @GetMapping("/appStatus/mal/kits")
    public MALKits getKits() { return malKits; }

    @GetMapping("/appStatus/bm/themes")
    public BMThemes getThemes() { return bmThemes; }
}
