package MediaPoolMalBridge.service.Bridge.database.assetunlocker;

import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BridgeDatabaseUnlockerSchedulerService extends AbstractSchedulerService {

    private final BridgeDatabaseUnlockerUniqueThreadService bridgeDatabaseUnlockerUniqueThreadService;

    public BridgeDatabaseUnlockerSchedulerService( final BridgeDatabaseUnlockerUniqueThreadService bridgeDatabaseUnlockerUniqueThreadService ) {
        this.bridgeDatabaseUnlockerUniqueThreadService = bridgeDatabaseUnlockerUniqueThreadService;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getBridgeAssetOnBoardingCronExpression()));
    }

    @Override
    public void scheduled() {
        logger.info("Searching updated assets started");
        bridgeDatabaseUnlockerUniqueThreadService.start();
        logger.info("Searching updated assets ended");
    }
}
