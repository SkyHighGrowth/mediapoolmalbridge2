package com.brandmaker.mediapoolmalbridge.service.bridge.sendmail;

import com.brandmaker.mediapoolmalbridge.service.AbstractSchedulerService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service that triggers exeuction of {@link BridgeSendMailUniqueThreadService}
 */
@Service
public class BridgeSendMailSchedulerService extends AbstractSchedulerService {

    private final BridgeSendMailUniqueThreadService bridgeSendMailUniqueThreadService;

    public BridgeSendMailSchedulerService(final BridgeSendMailUniqueThreadService bridgeSendMailUniqueThreadService) {
        this.bridgeSendMailUniqueThreadService = bridgeSendMailUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        String bridgeSendMailCronExpression = appConfig.getBridgeSendMailCronExpression();
        if (bridgeSendMailCronExpression != null) {
            jobSchedule(new CronTrigger(bridgeSendMailCronExpression));
        }
    }

    @Override
    public void scheduled() {
        logger.info("Sending mail started");
        bridgeSendMailUniqueThreadService.start();
        logger.info("Sending mail ended");

    }
}
