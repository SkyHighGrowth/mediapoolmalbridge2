package MediaPoolMalBridge.service.Bridge.assetstransfer;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetRepository;
import MediaPoolMalBridge.persistence.repository.MAL.MALAssetRepository;
import MediaPoolMalBridge.service.AbstractSchedulerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class BridgeAssetTransferSchedulerService extends AbstractSchedulerService {

    private final int pageSize = 20000;

    private final BMAssetRepository bmAssetRepository;

    private final MALAssetRepository malAssetRepository;

    private final MALToBMTransformer malToBMTransformer;

    @PersistenceContext
    private EntityManager entityManager;

    public BridgeAssetTransferSchedulerService(final BMAssetRepository bmAssetRepository,
                                               final MALAssetRepository malAssetRepository,
                                               final MALToBMTransformer malToBMTransformer) {
        this.bmAssetRepository = bmAssetRepository;
        this.malAssetRepository = malAssetRepository;
        this.malToBMTransformer = malToBMTransformer;
    }

    @PostConstruct
    public void init() {
        taskSchedulerWrapper.getTaskScheduler().schedule(this::run, new CronTrigger(appConfig.getBridgeExchangeAssetsCronExpression()));
    }

    @Override
    public void scheduled() {

        boolean condition = true;
        for( int page = 0; condition; ++page ) {
            final Slice<MALAssetEntity> malAssetEntities = malAssetRepository.findAllByTransferringMALConnectionAssetStatusOrTransferringAssetStatusAndUpdatedIsAfter(
                    TransferringMALConnectionAssetStatus.DOWNLOADED, TransferringAssetStatus.MAL_DELETED, getTodayMidnight(), PageRequest.of(page, pageSize));
            condition = malAssetEntities.hasNext();
            transformToBMAssetEntities( malAssetEntities );
        }
    }

    @Transactional
    protected void transformToBMAssetEntities( final Slice<MALAssetEntity> malAssetEntities )
    {
        malAssetEntities.forEach(malAssetEntity -> {
            BMAssetEntity bmAssetEntity;
            if( StringUtils.isNotBlank(malAssetEntity.getBmAssetId()) && !malAssetEntity.getBmAssetId().startsWith("CREATING_") ) {
                final Optional<BMAssetEntity> optionalBMAssetEntity = bmAssetRepository.findByAssetId(malAssetEntity.getBmAssetId());
                if (optionalBMAssetEntity.isPresent()) {
                    bmAssetEntity = optionalBMAssetEntity.get();
                    malToBMTransformer.update(bmAssetEntity, malAssetEntity);
                } else {
                    final String message = String.format( "Illegal state: mal asset [%s] table points to non existent mediapool asset", GSON.toJson(malAssetEntity) );
                    final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null, null );
                    reportsRepository.save( reportsEntity );
                    logger.error( message );
                    return;
                }
            } else {
                bmAssetEntity = malToBMTransformer.transform(malAssetEntity);
                if (bmAssetEntity == null) {
                    return;
                }
            }
            malAssetEntity.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DONE);
            bmAssetRepository.save(bmAssetEntity);
            malAssetRepository.save(malAssetEntity);
        });
    }
}
