package com.brandmaker.mediapoolmalbridge.service.mal.properties;

import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALSchedulerService;
import com.brandmaker.mediapoolmalbridge.service.mal.properties.deleted.MALGetPropertiesDeletedUniqueThreadSinceService;
import com.brandmaker.mediapoolmalbridge.service.mal.properties.download.MALGetPropertiesUniqueThreadService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service that triggers properties update and delete from MAL server
 */
@Service
public class MALGetPropertiesSchedulerService extends AbstractMALSchedulerService {

    private final MALGetPropertiesUniqueThreadService getPropertiesService;

    private final MALGetPropertiesDeletedUniqueThreadSinceService getPropertiesDeletedSinceService;

    public MALGetPropertiesSchedulerService(final MALGetPropertiesUniqueThreadService getPropertiesService,
                                            final MALGetPropertiesDeletedUniqueThreadSinceService getPropertiesDeletedSinceService) {
        this.getPropertiesService = getPropertiesService;
        this.getPropertiesDeletedSinceService = getPropertiesDeletedSinceService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getMalPropertiesCronExpression()));
    }

    @Override
    public void scheduled() {
        final String since = getMidnightMalLookInThePast();
        logger.info("Collecting properties from MAL started");
        getPropertiesService.start();
        logger.info("Collecting properties from MAL ended");
        logger.info("Collecting deleted properties from MAL started");
        getPropertiesDeletedSinceService.setUnavailableSince( since );
        getPropertiesDeletedSinceService.start();
        logger.info("Collecting deleted properties from MAL ended");
    }
}
