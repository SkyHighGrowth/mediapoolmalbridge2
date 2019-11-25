package MediaPoolMalBridge.service.Bridge.sendmail;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BridgeSendMailSchedulerService extends AbstractSchedulerService {

    private BridgeSendMailService bridgeSendMailService;

    public BridgeSendMailSchedulerService( final BridgeSendMailService bridgeSendMailService )
    {
        this.bridgeSendMailService = bridgeSendMailService;
    }

    @PostConstruct
    public void init()
    {
        taskSchedulerWrapper.getTaskScheduler().schedule( this::run, new CronTrigger(Constants.CRON_MIDNIGHT_TRIGGGER_EXPRESSION ) );
    }

    @Override
    public void scheduled() {
        bridgeSendMailService.start();
    }
}
