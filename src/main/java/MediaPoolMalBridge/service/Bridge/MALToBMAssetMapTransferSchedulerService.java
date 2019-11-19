package MediaPoolMalBridge.service.Bridge;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class MALToBMAssetMapTransferSchedulerService extends AbstractSchedulerService {

    private final BMAssetRepository bmAssetRepository;

    private final MALAssetRepository malAssetRepository;

    private final MALToBMTransformer malToBMTransformer;

    public MALToBMAssetMapTransferSchedulerService(final BMAssetRepository bmAssetRepository,
                                                   final MALAssetRepository malAssetRepository,
                                                   final MALToBMTransformer malToBMTransformer) {
        this.bmAssetRepository = bmAssetRepository;
        this.malAssetRepository = malAssetRepository;
        this.malToBMTransformer = malToBMTransformer;
    }

    @PostConstruct
    public void scheduleTransfer() {
        if (isRunScheduler()) {
            taskSchedulerWrapper.getTaskScheduler().schedule(this::transferAssets, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
        }
    }

    public void transferAssets() {
        final List<MALAssetEntity> malAssetEntities = malAssetRepository.findDownloadedOrMalDeleted( TransferringMALConnectionAssetStatus.DOWNLOADED, TransferringAssetStatus.MAL_DELETED );
        logger.debug( GSON.toJson( malAssetEntities ) );
        malAssetEntities.forEach(malAssetEntity -> {
            final BMAssetEntity bmAsset = malToBMTransformer.translate(malAssetEntity);
            malAssetEntity.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DONE);
            bmAssetRepository.save(bmAsset);
            malAssetRepository.save(malAssetEntity);
        });
    }
}
