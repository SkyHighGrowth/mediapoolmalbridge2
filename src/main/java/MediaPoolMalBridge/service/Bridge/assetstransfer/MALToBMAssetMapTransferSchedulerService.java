package MediaPoolMalBridge.service.Bridge.assetstransfer;

import MediaPoolMalBridge.constants.Constants;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class MALToBMAssetMapTransferSchedulerService extends AbstractSchedulerService {

    private final int pageSize = 20000;

    private final BMAssetRepository bmAssetRepository;

    private final MALAssetRepository malAssetRepository;

    private final MALToBMTransformer malToBMTransformer;

    @PersistenceContext
    private EntityManager entityManager;

    public MALToBMAssetMapTransferSchedulerService(final BMAssetRepository bmAssetRepository,
                                                   final MALAssetRepository malAssetRepository,
                                                   final MALToBMTransformer malToBMTransformer) {
        this.bmAssetRepository = bmAssetRepository;
        this.malAssetRepository = malAssetRepository;
        this.malToBMTransformer = malToBMTransformer;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(Constants.CRON_HOURLY_TRIGGGER_EXPRESSION));
    }

    @Override
    public void scheduled() {

        boolean condition = true;
        for( int page = 0; condition; ++page ) {
            final Slice<MALAssetEntity> malAssetEntities = malAssetRepository.findAllByTransferringMALConnectionAssetStatusOrTransferringAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADED, TransferringAssetStatus.MAL_DELETED, PageRequest.of(page, pageSize));
            condition = malAssetEntities.hasNext();
            transformToBMAssetEntities( malAssetEntities );
        }
    }

    @Transactional
    protected void transformToBMAssetEntities( final Slice<MALAssetEntity> malAssetEntities )
    {
        malAssetEntities.forEach(malAssetEntity -> {
            final BMAssetEntity bmAsset = malToBMTransformer.transform(malAssetEntity);
            malAssetEntity.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DONE);
            bmAssetRepository.save(bmAsset);
            malAssetRepository.save(malAssetEntity);
        });
    }
}
