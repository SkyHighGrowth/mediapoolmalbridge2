package MediaPoolMalBridge.service.Bridge.database.normalizer;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetJsonedValuesRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.AbstractService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service( "BridgeDatabaseNormalizerService" )
public class BridgeDatabaseNormalizerService extends AbstractService {

    private AssetRepository assetRepository;

    //private MALPropertyRepository malPropertyRepository;

    private UploadedFileRepository uploadedFileRepository;

    private AssetJsonedValuesRepository assetJsonedValuesRepository;

    public BridgeDatabaseNormalizerService(final AssetRepository assetRepository,
                                           //final MALPropertyRepository malPropertyRepository,
                                           final AssetJsonedValuesRepository assetJsonedValuesRepository,
                                           final UploadedFileRepository uploadedFileRepository) {
        this.assetRepository = assetRepository;
        //this.malPropertyRepository = malPropertyRepository;
        this.assetJsonedValuesRepository = assetJsonedValuesRepository;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @PostConstruct
    public void init() {
        normalize( getMidnightBridgeLookInThePast(), TransferringAssetStatus.FILE_DOWNLOADING, TransferringAssetStatus.ASSET_ONBOARDED);
        normalize( getMidnightBridgeLookInThePast(), TransferringAssetStatus.FILE_UPLOADING, TransferringAssetStatus.FILE_DOWNLOADED );
        normalize( getMidnightBridgeLookInThePast(), TransferringAssetStatus.METADATA_UPLOADING, TransferringAssetStatus.FILE_UPLOADED );
        normalize( getMidnightBridgeLookInThePast(), TransferringAssetStatus.METADATA_DOWNLOADING, TransferringAssetStatus.METADATA_UPLOADED );
        normalize( getMidnightBridgeLookInThePast(), TransferringAssetStatus.GETTING_BM_ASSET_ID,  TransferringAssetStatus.GET_BM_ASSET_ID );
        //importProperties();
        //transferJsonedValues();
    }

    private void normalize(final LocalDateTime midnight, final TransferringAssetStatus from, TransferringAssetStatus to )
    {
        boolean condition = true;
        for(int page = 0; condition; ++page ) {
            final List<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(from, midnight, PageRequest.of(0, appConfig.getDatabasePageSize() ) );
            if( assetEntities.isEmpty() || page > 5000 ) {
                break;
            }
            normalizePage( assetEntities, to );
        }
    }

    @Transactional
    protected void normalizePage( final List<AssetEntity> assetEntities, final TransferringAssetStatus to )
    {
        assetEntities.forEach(assetEntity -> {
            assetEntity.setTransferringAssetStatus( to );
            assetRepository.save( assetEntity );
            if( TransferringAssetStatus.ASSET_ONBOARDED.equals( assetEntity.getTransferringAssetStatus() ) ) {
                uploadedFileRepository.save(new UploadedFileEntity(assetEntity.getFileNameOnDisc()));
            }
        });
    }
}
