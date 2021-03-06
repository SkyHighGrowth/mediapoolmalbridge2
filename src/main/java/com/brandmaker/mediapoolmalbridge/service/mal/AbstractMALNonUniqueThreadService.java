package com.brandmaker.mediapoolmalbridge.service.mal;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.repository.mal.MALPropertyRepository;
import com.brandmaker.mediapoolmalbridge.service.AbstractNonUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.tasks.TaskExecutorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Class that implements common fields and methods of MAL server non unique thread services
 * @param <T>
 */
public abstract class AbstractMALNonUniqueThreadService<T> extends AbstractNonUniqueThreadService<T> {

    @Autowired
    @Qualifier( "MALTaskExecutorWrapper" )
    protected TaskExecutorWrapper taskExecutorWrapper;

    @Override
    protected TaskExecutorWrapper getTaskExecutorWrapper() {
        return taskExecutorWrapper;
    }

    protected boolean isGateOpen(final AssetEntity assetEntity, final String serviceDescription, final MALAbstractResponse malAbstractResponse, final Exception e ) {
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAppConfigData().getAssetStateRepetitionMax() ) {
            final String message;
            if( malAbstractResponse == null ) {
                message = String.format( "Max retries for %s achieved for asset id [%s], with message [%s]", serviceDescription, assetEntity.getMalAssetId(), e.getMessage() );
            } else {
                message = String.format( "Max retries for %s achieved for asset id [%s], with message [%s]", serviceDescription, assetEntity.getMalAssetId(), malAbstractResponse.getMessage() );
            }
            String toJson = GSON.toJson(assetEntity);
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), assetEntity.getMalAssetId(), message, ReportTo.MAL, toJson, null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, toJson);
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ERROR );
            assetRepository.save( assetEntity );
            return false;
        }
        assetRepository.save( assetEntity );
        return true;
    }

    @Autowired
    protected MALPropertyRepository malPropertyRepository;
}
