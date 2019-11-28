package MediaPoolMalBridge.service.MAL.kits.controller;

import MediaPoolMalBridge.service.MAL.kits.MALGetKitsUniqueThreadService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetKitsController {

    private MALGetKitsUniqueThreadService malGetKitsUniqueThreadService;

    public MALGetKitsController(final MALGetKitsUniqueThreadService malGetKitsUniqueThreadService) {
        this.malGetKitsUniqueThreadService = malGetKitsUniqueThreadService;
    }

    @GetMapping("/service/mal/getKits")
    public void getKits() {
        malGetKitsUniqueThreadService.start();
    }
}
