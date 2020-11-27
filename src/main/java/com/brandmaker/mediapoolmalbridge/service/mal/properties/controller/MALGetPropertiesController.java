package com.brandmaker.mediapoolmalbridge.service.mal.properties.controller;

import com.brandmaker.mediapoolmalbridge.service.mal.properties.MALGetPropertiesSchedulerService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that triggers {@link MALGetPropertiesSchedulerService}
 */
@RestController
@Profile("enable controllers")
public class MALGetPropertiesController {

    private final MALGetPropertiesSchedulerService getPropertiesSchedulerService;

    public MALGetPropertiesController(final MALGetPropertiesSchedulerService getPropertiesSchedulerService) {
        this.getPropertiesSchedulerService = getPropertiesSchedulerService;
    }

    @GetMapping("/service/mal/getProperties")
    public void getProperties() {
        getPropertiesSchedulerService.scheduled();
    }
}
