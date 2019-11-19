package MediaPoolMalBridge.service.MAL.kits.controller;

import MediaPoolMalBridge.service.MAL.kits.MALGetKitsService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetKitsController {

    private MALGetKitsService malGetKitsService;

    public MALGetKitsController(final MALGetKitsService malGetKitsService) {
        this.malGetKitsService = malGetKitsService;
    }

    @GetMapping("/service/mal/getKits")
    public void getKits() {
        malGetKitsService.downloadKits();
    }
}
