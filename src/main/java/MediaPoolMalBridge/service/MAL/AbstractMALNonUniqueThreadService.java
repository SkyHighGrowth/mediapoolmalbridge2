package MediaPoolMalBridge.service.MAL;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.AbstractNonUniqueThreadService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Class that implements common fields and methods of MAL server non unique thread services
 * @param <RUN_ARGUMENT>
 */
public abstract class AbstractMALNonUniqueThreadService<RUN_ARGUMENT> extends AbstractNonUniqueThreadService<RUN_ARGUMENT> {

    @Autowired
    @Qualifier( "MALTaskExecutorWrapper" )
    protected TaskExecutorWrapper taskExecutorWrapper;

    @Override
    protected TaskExecutorWrapper getTaskExecutorWrapper() {
        return taskExecutorWrapper;
    }

    protected boolean isGateOpen(final AssetEntity assetEntity, final String serviceDescription, final MALAbstractResponse malAbstractResponse, final Exception e ) {
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message;
            if( malAbstractResponse == null ) {
                message = String.format( "Max retries for %s achieved for asset id [%s], with message [%s]", serviceDescription, assetEntity.getMalAssetId(), e.getMessage() );
            } else {
                message = String.format( "Max retries for %s achieved for asset id [%s], with message [%s]", serviceDescription, assetEntity.getMalAssetId(), malAbstractResponse.getMessage() );
            }
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), assetEntity.getMalAssetId(), message, ReportTo.MAL, GSON.toJson( assetEntity ), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
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
