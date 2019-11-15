package MediaPoolMalBridge.service.Bridge;

import MediaPoolMalBridge.clients.BrandMaker.model.BMAsset;
import MediaPoolMalBridge.clients.MAL.model.MALAsset;
import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.model.BrandMaker.BMAssetMap;
import MediaPoolMalBridge.model.MAL.MALAssetMap;
import MediaPoolMalBridge.model.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MALToBMAssetMapTransferSchedulerService extends AbstractSchedulerService {

    private final MALAssetMap malAssetMap;

    private final BMAssetMap bmAssetMap;

    private final MALToBMTranslator malToBMTranslator;

    public MALToBMAssetMapTransferSchedulerService(final MALAssetMap malAssetMap,
                                                   final BMAssetMap bmAssetMap,
                                                   final MALToBMTranslator malToBMTranslator)
    {
        this.malAssetMap = malAssetMap;
        this.bmAssetMap = bmAssetMap;
        this.malToBMTranslator = malToBMTranslator;
    }

    @PostConstruct
    public void scheduleTransfer()
    {
        if( isRunScheduler() ) {
            taskSchedulerWrapper.getTaskScheduler().schedule(this::transferAssets, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
        }
    }

    public void transferAssets()
    {
        malAssetMap.values()
                .stream()
                .filter( transferringAsset -> TransferringMALConnectionAssetStatus.DOWNLOADED.equals(transferringAsset.getTransferringMALConnectionAssetStatus()))
                .forEach( transferringAsset -> {
                    if( transferringAsset instanceof MALAsset )
                    {
                        final MALAsset malAsset = (MALAsset) transferringAsset;
                        final BMAsset bmAsset = malToBMTranslator.translate( malAsset );
                        bmAssetMap.put( bmAsset.getAssetId(), bmAsset );
                        malAssetMap.remove( malAsset.getMALAssetId() );
                    }
                });
    }
}
