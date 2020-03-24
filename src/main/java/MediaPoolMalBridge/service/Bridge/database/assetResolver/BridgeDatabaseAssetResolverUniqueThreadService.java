package MediaPoolMalBridge.service.Bridge.database.assetResolver;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.Bridge.AbstractBridgeUniqueThreadService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BridgeDatabaseAssetResolverUniqueThreadService extends AbstractBridgeUniqueThreadService {

    @Autowired
    @Qualifier( "GsonSerializer" )
    private Gson gson;

    private UploadedFileRepository uploadedFileRepository;

    public BridgeDatabaseAssetResolverUniqueThreadService( final UploadedFileRepository uploadedFileRepository ) {
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @Override
    protected void run() {
        logger.info( "running resolver" );
        for( int page = 0; true; ++page ) {
            logger.info( "reolving page {}", page );
            final List<AssetEntity> assetEntities = assetRepository.findAllByUpdatedIsAfterAndUpdatedIsBeforeAndTransferringAssetStatusIsNotAndTransferringAssetStatusIsNot(
                    getMidnightBridgeLookInThePast( ).minusDays( appConfig.getBridgeResolverWindow() ), getMidnightBridgeLookInThePast(), TransferringAssetStatus.DONE, TransferringAssetStatus.ERROR, PageRequest.of( 0, appConfig.getDatabasePageSize() ) );
            if( assetEntities.isEmpty() || page > 1000 ) {
                break;
            }
            resolvePage( assetEntities );
        }
    }

    @Transactional
    protected void resolvePage( final List<AssetEntity> assetEntities ) {
        assetEntities.forEach(assetEntity -> {
            logger.info( "resolving asset {}", assetEntity.getMalAssetId() );
            final String message = String.format( "Asset with id [%s] transfer ended in status [%s] and total transfer has not finished successfully", assetEntity.getMalAssetId(), assetEntity.getTransferringAssetStatus().name());
            final ReportsEntity reportMal = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, gson.toJson(assetEntity), null, null);
            reportsRepository.save( reportMal );
            final ReportsEntity reportBM = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.BM, gson.toJson(assetEntity), null, null);
            reportsRepository.save( reportBM );
            logger.error( message + " asset {}", gson.toJson(assetEntity) );
            if( StringUtils.isNotBlank( assetEntity.getFileNameOnDisc() ) &&
                !TransferringAssetStatus.ASSET_OBSERVED.equals( assetEntity.getTransferringAssetStatus() ) )
            {
                uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
            }
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ERROR );
            assetRepository.save( assetEntity );
        });
    }
}
