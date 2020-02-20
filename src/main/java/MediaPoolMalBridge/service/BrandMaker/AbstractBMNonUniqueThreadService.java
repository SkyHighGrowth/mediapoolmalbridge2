package MediaPoolMalBridge.service.BrandMaker;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.AbstractNonUniqueThreadService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import MediaPoolMalBridge.tasks.TaskPriorityExecutorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class that serves as a base class for Mediapool Service classes which are executed in
 * {@link org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor}
 * @param <RUN_ARGUMENT> - argument that should be given when execution is triggered
 */
public abstract class AbstractBMNonUniqueThreadService<RUN_ARGUMENT> extends AbstractNonUniqueThreadService<RUN_ARGUMENT> {

    @Autowired
    @Qualifier( "BMTaskPriorityExecutorWrapper" )
    protected TaskPriorityExecutorWrapper taskExecutorWrapper;

    @Override
    protected TaskExecutorWrapper getTaskExecutorWrapper() {
        return taskExecutorWrapper;
    }

    @Transactional
    protected boolean isGateOpen(final AssetEntity assetEntity, final String serviceDescription, AbstractBMResponse abstractBMResponse ) {
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for %s for asset id [%s], with messages [%s] and warnings [%s]", serviceDescription, assetEntity.getBmAssetId(), abstractBMResponse.getErrorAsString(), abstractBMResponse.getWarningsAsString() );
            //logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), assetEntity.getMalAssetId(), message, ReportTo.BM, GSON.toJson(assetEntity), null, null );
            reportsRepository.save( reportsEntity );
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ERROR );
            assetRepository.save( assetEntity );
            return false;
        }
        assetRepository.save( assetEntity );
        return true;
    }

    @Transactional
    protected void reportErrorOnResponse(final String assetId, final AbstractBMResponse abstractBMResponse) {
        final String message = String.format("Can not perform operation [%s] for asset with id [%s], with error message [%s] and warnings [%s]", getClass().getName(), assetId, abstractBMResponse.getErrorAsString(), abstractBMResponse.getWarningsAsString());
        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), assetId, message, ReportTo.NONE, null, null, null );
        reportsRepository.save( reportsEntity );
    }
}
