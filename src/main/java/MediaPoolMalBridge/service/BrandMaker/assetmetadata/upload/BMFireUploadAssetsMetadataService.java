package MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class BMFireUploadAssetsMetadataService extends AbstractBMUniqueService {

    private final int pageSize = 1000;

    private final BMUploadAssetMetadataService bmUploadAssetMetadataService;

    public BMFireUploadAssetsMetadataService(final BMUploadAssetMetadataService bmUploadAssetMetadataService ) {
        this.bmUploadAssetMetadataService = bmUploadAssetMetadataService;
    }

    @Override
    protected void run() {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<BMAssetEntity> bmAssetEntities = bmAssetRepository.findAllByTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatus(
                    TransferringBMConnectionAssetStatus.FILE_CREATED, TransferringBMConnectionAssetStatus.FILE_UPLOADED, TransferringBMConnectionAssetStatus.METADATA_UPLOADING, PageRequest.of(page, pageSize));
            condition = bmAssetEntities.hasNext();
            bmAssetEntities.forEach( bmAssetEntity -> {
                while( true ) {
                    if( taskExecutorWrapper.getTaskExecutor().getThreadPoolExecutor().getQueue().size() > 9000 ) {
                        try {
                            synchronized( bmUploadAssetMetadataService ) {
                                bmUploadAssetMetadataService.wait(10000);
                            }
                        } catch ( final InterruptedException e ) {
                            throw new RuntimeException();
                        }
                    } else {
                        break;
                    }
                }
                taskExecutorWrapper.getTaskExecutor()
                        .execute(() -> bmUploadAssetMetadataService.start(bmAssetEntity));
            } );
        }
    }
}
