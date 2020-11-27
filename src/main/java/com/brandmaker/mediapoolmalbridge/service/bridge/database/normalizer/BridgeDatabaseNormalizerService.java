package com.brandmaker.mediapoolmalbridge.service.bridge.database.normalizer;

import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.UploadedFileEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.AssetRepository;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.UploadedFileRepository;
import com.brandmaker.mediapoolmalbridge.service.AbstractService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service( "BridgeDatabaseNormalizerService" )
public class BridgeDatabaseNormalizerService extends AbstractService {

    private final AssetRepository assetRepository;

    private final UploadedFileRepository uploadedFileRepository;


    public BridgeDatabaseNormalizerService(final AssetRepository assetRepository,
                                           final UploadedFileRepository uploadedFileRepository) {
        this.assetRepository = assetRepository;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @PostConstruct
    public void init() {
        normalize( getMidnightBridgeLookInThePast(), TransferringAssetStatus.FILE_DOWNLOADING, TransferringAssetStatus.ASSET_ONBOARDED);
        normalize( getMidnightBridgeLookInThePast(), TransferringAssetStatus.FILE_UPLOADING, TransferringAssetStatus.FILE_DOWNLOADED );
        normalize( getMidnightBridgeLookInThePast(), TransferringAssetStatus.METADATA_UPLOADING, TransferringAssetStatus.FILE_UPLOADED );
        normalize( getMidnightBridgeLookInThePast(), TransferringAssetStatus.METADATA_DOWNLOADING, TransferringAssetStatus.METADATA_UPLOADED );
        normalize( getMidnightBridgeLookInThePast(), TransferringAssetStatus.GETTING_BM_ASSET_ID,  TransferringAssetStatus.GET_BM_ASSET_ID );
    }

    private void normalize(final LocalDateTime midnight, final TransferringAssetStatus from, TransferringAssetStatus to )
    {
        for(int page = 0; true; ++page ) {
            final List<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(from, midnight, PageRequest.of(0, appConfig.getDatabasePageSize() ) );
            if( assetEntities.isEmpty() || page > 5000 ) {
                break;
            }
            normalizePage( assetEntities, to );
        }
    }

    @Transactional
    public void normalizePage( final List<AssetEntity> assetEntities, final TransferringAssetStatus to )
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
