package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.controller;

import MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.MALPhotoSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALPhotoSchedulerController {

    private final MALPhotoSchedulerService malPhotoSchedulerService;

    public MALPhotoSchedulerController(final MALPhotoSchedulerService malPhotoSchedulerService) {
        this.malPhotoSchedulerService = malPhotoSchedulerService;
    }

    @GetMapping("/service/mal/updateModifiedPhotos")
    public void getUpdate() {
        malPhotoSchedulerService.scheduled();
    }

    @GetMapping("/service/mal/downloadModifiedPhotos")
    public void downloadPhotos() {
        malPhotoSchedulerService.scheduled();
    }
}
