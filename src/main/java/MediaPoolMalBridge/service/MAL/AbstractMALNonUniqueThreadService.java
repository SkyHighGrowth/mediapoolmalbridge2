package MediaPoolMalBridge.service.MAL;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.MAL.MALPropertyRepository;
import MediaPoolMalBridge.service.AbstractNonUniqueThreadService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class that implements common fields and methods of MAL server non unique thread services
 * @param <RUN_ARGUMENT>
 */
public abstract class AbstractMALNonUniqueThreadService<RUN_ARGUMENT> extends AbstractNonUniqueThreadService<RUN_ARGUMENT> {

    protected boolean isGateOpen( final AssetEntity assetEntity, final String serviceDescription ) {
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for %s achieved for asset id [%s]", serviceDescription, assetEntity.getMalAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson( assetEntity ), null, null );
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
