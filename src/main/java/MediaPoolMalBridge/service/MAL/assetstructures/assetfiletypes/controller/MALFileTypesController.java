package MediaPoolMalBridge.service.MAL.assetstructures.assetfiletypes.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.assetfiletypes.MALGetAssetFileTypesService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALFileTypesController {

    private final MALGetAssetFileTypesService getFileTypesSchedulerService;

    public MALFileTypesController(final MALGetAssetFileTypesService getFileTypesSchedulerService) {
        this.getFileTypesSchedulerService = getFileTypesSchedulerService;
    }

    @GetMapping("/service/mal/downloadFileTypes")
    public void setFileType() {
        getFileTypesSchedulerService.start();
    }
}
