package MediaPoolMalBridge.service.MAL.assets.download.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.transformer.MAL.MALAssetModelsToMALAssetEntityTransformer;
import MediaPoolMalBridge.service.MAL.AbstractMALNonUniqueThreadService;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile("dev")
public class MALGetAssetsDev extends AbstractMALNonUniqueThreadService<MALGetAssetsRequest> {

    @Autowired
    private MALGetAssetsClient getAssetsClient;

    @Autowired
    private MALAssetModelsToMALAssetEntityTransformer malAssetModelsToMALAssetEntityTransformer;

    public void run(final MALGetAssetsRequest request) {
        transformPagesIntoAssets(request, 1);
    }

    private void transformPagesIntoAssets(final MALGetAssetsRequest request, final int totalPages) {
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
                            final MALAssetEntity malAssetEntity = putIntoMALAssetMap(malGetAsset, MALAssetType.FILE);
                            if( malAssetEntity != null ) {
                                malAssetRepository.save( malAssetEntity );
                            }
                        }

                        if ( StringUtils.isNotBlank( malGetAsset.getLogoJpgUrl() ) ) {
                            final MALAssetEntity malAssetEntity = putIntoMALAssetMap(malGetAsset, MALAssetType.JPG_LOGO);
                            if( malAssetEntity != null ) {
                                malAssetRepository.save( malAssetEntity );
                            }
                        }

                        if ( StringUtils.isNotBlank( malGetAsset.getLogoPngUrl() ) ) {
                            final MALAssetEntity malAssetEntity = putIntoMALAssetMap(malGetAsset, MALAssetType.PNG_LOGO);
                            if( malAssetEntity != null ) {
                                malAssetRepository.save( malAssetEntity );
                            }
                        }
                    });
        }
    }

    private MALAssetEntity putIntoMALAssetMap(final MALGetAsset malGetAsset, final MALAssetType malAssetType) {
        final Optional<MALAssetEntity> optionalMALAssetEntity = malAssetRepository.findByAssetIdAndAssetType( malGetAsset.getAssetId(), malAssetType );
        MALAssetEntity malAssetEntity;
        if (optionalMALAssetEntity.isPresent()) {
            malAssetEntity = optionalMALAssetEntity.get();
            final String md5Hash = DigestUtils.md5Hex( GSON.toJson( malGetAsset ) );
            logger.error( "malAssetEntity modified {}, downloaded modified{}, md5Hash {}, downloaded md5hash {}, bmAsset {}", malAssetEntity.getMalLastModified(), malGetAsset.getLastModified(), md5Hash, malAssetEntity.getMd5Hash(), malAssetEntity.getBmAssetId());
            if( malAssetEntity.getMalLastModified().equals( malGetAsset.getLastModified() ) && md5Hash.equals( malAssetEntity.getMd5Hash() ) ) {
                logger.error( "not modified" );
                return null;
            }
            if( StringUtils.isNotBlank(malAssetEntity.getBmAssetId()) && malAssetEntity.getBmAssetId().startsWith( "CREATING_") )
            {
                logger.error( "should go to existing MAL_CREATED" );
                malAssetEntity = malAssetModelsToMALAssetEntityTransformer.fromMALGetAssetCreate( optionalMALAssetEntity.get(), malGetAsset, malAssetType);
            } else {
                logger.error( "should go to MAL_UPDATE" );
                malAssetEntity = malAssetModelsToMALAssetEntityTransformer.fromMALGetAssetUpdate(optionalMALAssetEntity.get(), malGetAsset, malAssetType);
            }
        } else {
            logger.error( "should go to MAL_CREATED" );
            malAssetEntity = malAssetModelsToMALAssetEntityTransformer.fromMALGetAssetCreate(malGetAsset, malAssetType);
        }
        if( StringUtils.isBlank( malAssetEntity.getUrl() ) )
        {
            final String message = String.format( "Found asset with id [%s] with invalid download url", malGetAsset.getAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, (new Gson()).toJson( malGetAsset), null, null);
            malAssetEntity.setTransferringAssetStatus( TransferringAssetStatus.INVALID );
        }
        return malAssetEntity;
    }
}
