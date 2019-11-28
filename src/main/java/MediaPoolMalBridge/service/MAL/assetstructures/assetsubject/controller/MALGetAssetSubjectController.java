package MediaPoolMalBridge.service.MAL.assetstructures.assetsubject.controller;

import MediaPoolMalBridge.service.MAL.assetstructures.assetsubject.MALGetAssetSubjectUniqueThreadService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetAssetSubjectController {

    private final MALGetAssetSubjectUniqueThreadService getAssetSubjectService;

    public MALGetAssetSubjectController(final MALGetAssetSubjectUniqueThreadService getAssetSubjectService) {
        this.getAssetSubjectService = getAssetSubjectService;
    }

    @GetMapping("/service/mal/getSubjects")
    public void download() {
        getAssetSubjectService.start();
    }
}
