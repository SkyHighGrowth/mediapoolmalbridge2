package MediaPoolMalBridge.service.MAL.properties.controller;

import MediaPoolMalBridge.service.MAL.properties.MALGetPropertiesSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class MALGetPropertiesController {

    private MALGetPropertiesSchedulerService getPropertiesSchedulerService;

    public MALGetPropertiesController(final MALGetPropertiesSchedulerService getPropertiesSchedulerService) {
        this.getPropertiesSchedulerService = getPropertiesSchedulerService;
    }

    @GetMapping("/service/mal/getProperties")
    public void getProperties() {
        getPropertiesSchedulerService.scheduled();
    }
}
