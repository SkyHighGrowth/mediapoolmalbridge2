package MediaPoolMalBridge.service.MAL.assets.download.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetIdEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetIdRepository;
import MediaPoolMalBridge.persistence.transformer.MAL.MALAssetModelsToMALAssetEntityTransformer;
import MediaPoolMalBridge.service.MAL.AbstractMALNonUniqueThreadService;
import MediaPoolMalBridge.service.MAL.assets.transformer.MALToBMTransformer;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("dev")
public class MALGetAssetsDev extends AbstractMALNonUniqueThreadService<MALGetAssetsRequest> {

    @Autowired
    private MALGetAssetsClient getAssetsClient;

    @Autowired
    private MALToBMTransformer malToBMTransformer;

    @Autowired
    private MALAssetModelsToMALAssetEntityTransformer assetModelsToMALAssetEntityTransformer;

    @Autowired
    private BMAssetIdRepository bmAssetIdRepository;

    public void run(final MALGetAssetsRequest request) {
        transformPagesIntoAssets(request, 1);
    }

    private void transformPagesIntoAssets(final MALGetAssetsRequest request, final int totalPages) {
        logger.error( "REQUEST {}", GSON.toJson(request));
        for (int page = 0; page < totalPages; ++page) {
            request.setPage(page + 1);
            RestResponse<MALGetAssetsResponse> response = getAssetsClient.download(request);
            if (!response.isSuccess() ||
                    response.getResponse() == null ||
                    response.getResponse().getAssets() == null) {
                continue;
            }
            response.getResponse()
                    .getAssets()
                    .forEach(malGetAsset -> {
                        if ( StringUtils.isNotBlank(malGetAsset.getThumbnailUrl()) ||
                                StringUtils.isNotBlank(malGetAsset.getMediumUrl()) ||
                                StringUtils.isNotBlank(malGetAsset.getLargeUrl()) ||
                                StringUtils.isNotBlank(malGetAsset.getXlUrl() ) ) {
                            final AssetEntity assetEntity = putIntoAssetMap(malGetAsset, MALAssetType.FILE);
                            if( assetEntity != null ) {
                                assetRepository.save( assetEntity );
                            }
                        }

                        if ( StringUtils.isNotBlank( malGetAsset.getLogoJpgUrl() ) ) {
                            final AssetEntity assetEntity = putIntoAssetMap(malGetAsset, MALAssetType.JPG_LOGO);
                            if( assetEntity != null ) {
                                assetRepository.save( assetEntity );
                            }
                        }

                        if ( StringUtils.isNotBlank( malGetAsset.getLogoPngUrl() ) ) {
                            final AssetEntity assetEntity = putIntoAssetMap(malGetAsset, MALAssetType.PNG_LOGO);
                            if( assetEntity != null ) {
                                assetRepository.save( assetEntity );
                            }
                        }
                    });
        }
    }

    private AssetEntity putIntoAssetMap(final MALGetAsset malGetAsset, final MALAssetType assetType) {

        final String malMd5Hash = DigestUtils.md5Hex( GSON.toJson( malGetAsset ) );
        final List<AssetEntity> dbAssetEntities = assetRepository.findAllByMalAssetIdAndAssetType( malGetAsset.getAssetId(), assetType );

        final AssetEntity assetEntity;
        logger.error( "Assets from database {}", dbAssetEntities.size());
        if( !dbAssetEntities.isEmpty() ) {
            for( final AssetEntity aE : dbAssetEntities ) {
                if( StringUtils.isNotBlank( aE.getMalLastModified() ) &&
                        aE.getMalLastModified().equals( malGetAsset.getLastModified() ) &&
                        malMd5Hash.equals( aE.getMalMd5Hash() ) ) {
                    return null;
                }
            }
            assetEntity = assetModelsToMALAssetEntityTransformer.fromMALGetAsset( malGetAsset, assetType );
            assetEntity.setBmAssetIdEntity( dbAssetEntities.get( 0 ).getBmAssetIdEntity() );
            assetEntity.setMalAssetOperation( MALAssetOperation.MAL_MODIFIED );
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ASSET_OBSERVED );
        } else {
            assetEntity = assetModelsToMALAssetEntityTransformer.fromMALGetAsset( malGetAsset, assetType );
            final BMAssetIdEntity bmAssetIdEntity = new BMAssetIdEntity( "CREATING_" + malGetAsset.getAssetId() );
            bmAssetIdRepository.save( bmAssetIdEntity );
            assetEntity.setBmAssetIdEntity( bmAssetIdEntity );
            assetEntity.setMalAssetOperation( MALAssetOperation.MAL_CREATED );
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ASSET_OBSERVED );
        }
        assetEntity.setMalMd5Hash( malMd5Hash );
        assetEntity.setBmUploadMetadataArgument( malToBMTransformer.transformToUploadMetadataArgument( malGetAsset ) );

        if( StringUtils.isBlank( assetEntity.getUrl() ) )
        {
            final String message = String.format( "Found asset with id [%s] with invalid download url", malGetAsset.getAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, (new Gson()).toJson( malGetAsset), null, null);
            reportsRepository.save( reportsEntity );
            assetEntity.setMalAssetOperation( MALAssetOperation.INVALID );
        }

        return assetEntity;
    }
}
