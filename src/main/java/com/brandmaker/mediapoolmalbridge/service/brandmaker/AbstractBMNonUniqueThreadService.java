package com.brandmaker.mediapoolmalbridge.service.brandmaker;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.AbstractNonUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.tasks.TaskExecutorWrapper;
import com.brandmaker.mediapoolmalbridge.tasks.TaskPriorityExecutorWrapper;
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
    public boolean isGateOpen(final AssetEntity assetEntity, final String serviceDescription, AbstractBMResponse abstractBMResponse ) {
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAppConfigData().getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for %s for asset id [%s], with messages [%s] and warnings [%s]", serviceDescription, assetEntity.getBmAssetId(), abstractBMResponse.getErrorAsString(), abstractBMResponse.getWarningsAsString() );
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
    public void reportErrorOnResponse(final String assetId, final AbstractBMResponse abstractBMResponse) {
        final String message = String.format("Can not perform operation [%s] for asset with id [%s], with error message [%s] and warnings [%s]", getClass().getName(), assetId, abstractBMResponse.getErrorAsString(), abstractBMResponse.getWarningsAsString());
        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), assetId, message, ReportTo.NONE, null, null, null );
        reportsRepository.save( reportsEntity );
    }
}
