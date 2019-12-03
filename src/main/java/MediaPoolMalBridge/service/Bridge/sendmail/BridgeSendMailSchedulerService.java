package MediaPoolMalBridge.service.Bridge.sendmail;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Scheduler service that triggers exeuction of {@link BridgeSendMailUniqueThreadService}
 */
@Service
public class BridgeSendMailSchedulerService extends AbstractSchedulerService {

    private BridgeSendMailUniqueThreadService bridgeSendMailUniqueThreadService;

    public BridgeSendMailSchedulerService( final BridgeSendMailUniqueThreadService bridgeSendMailUniqueThreadService)
    {
        this.bridgeSendMailUniqueThreadService = bridgeSendMailUniqueThreadService;
    }

    @PostConstruct
    public void init()
    {
        taskSchedulerWrapper.getTaskScheduler().schedule( this::run, new CronTrigger(appConfig.getBridgeSendMailCronExpression() ) );
    }

    @Override
    public void scheduled() {
        bridgeSendMailUniqueThreadService.start();
    }
}
