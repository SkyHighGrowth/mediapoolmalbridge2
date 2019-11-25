package MediaPoolMalBridge.service.MAL.assets;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAsset;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.transformer.MAL.MALAssetModelsToMALAssetEntityTransformer;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueService;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractMALAssetsUniqueService extends AbstractMALUniqueService {

    @Autowired
    private MALGetAssetsClient getAssetsClient;

    @Autowired
    private MALAssetModelsToMALAssetEntityTransformer malAssetModelsToMALAssetEntityTransformer;

    private String since;

    protected void downloadAssets(final MALGetAssetsRequest request) {
        request.setPage(1);
        RestResponse<MALGetAssetsResponse> response = getAssetsClient.download(request);
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                StringUtils.isBlank(response.getResponse().getTotalPages()) ||
                response.getResponse().getAssets() == null) {
            return;
        }

        final int totalPages;
        try {
            totalPages = Integer.parseInt(response.getResponse().getTotalPages());
        } catch (final Exception e) {
            final String message = String.format("Can not parse totalPages of created asset since [%s], with httpStatus [%s], and responseBody [%s]",
                    since,
                    response.getHttpStatus(),
                    GSON.toJson(response.getResponse()));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message, e);
            return;
        }

        transformPagesIntoAssets(request, totalPages);
    }

    protected void transformPagesIntoAssets(final MALGetAssetsRequest request, final int totalPages) {
        for (int page = 0; page < totalPages; ++page) {
            try {
                request.setPage(page + 1);
                RestResponse<MALGetAssetsResponse> response = getAssetsClient.download(request);
                if (!response.isSuccess() ||
                        response.getResponse() == null ||
                        response.getResponse().getAssets() == null) {
                    continue;
                }

                transformPageIntoAssets( response.getResponse().getAssets() );
            }
            catch( final Exception e )
            {
                //entityManager.getTransaction().rollback();
                final String message = String.format( "Problem storing page [%s] to database with message [%s]", page, e.getMessage() );
                final ReportsEntity reportsEntity = new ReportsEntity( ReportType.WARNING, getClass().getName(), message, ReportTo.BM, null, null, null );
                reportsRepository.save( reportsEntity );
                logger.error( message );
            }
        }
    }

    @Transactional
    protected void transformPageIntoAssets(final List<MALGetAsset> malGetAssets)
    {
        malGetAssets.forEach(malGetAsset -> {
                    if (StringUtils.isNotBlank(malGetAsset.getThumbnailUrl()) ||
                            StringUtils.isNotBlank(malGetAsset.getMediumUrl()) ||
                            StringUtils.isNotBlank(malGetAsset.getLargeUrl()) ||
                            StringUtils.isNotBlank(malGetAsset.getXlUrl())) {
                        final MALAssetEntity malAssetEntity = putIntoMALAssetMap(malGetAsset, MALAssetType.FILE);
                        if( malAssetEntity != null ) {
                            malAssetRepository.save( malAssetEntity );
                        }
                    }

                    if (StringUtils.isNotBlank(malGetAsset.getLogoJpgUrl())) {
                        final MALAssetEntity malAssetEntity = putIntoMALAssetMap(malGetAsset, MALAssetType.JPG_LOGO);
                        if( malAssetEntity != null ) {
                            malAssetRepository.save( malAssetEntity );
                        }
                    }

                    if (StringUtils.isNotBlank(malGetAsset.getLogoPngUrl())) {
                        final MALAssetEntity malAssetEntity = putIntoMALAssetMap(malGetAsset, MALAssetType.PNG_LOGO);
                        if( malAssetEntity != null ) {
                            malAssetRepository.save( malAssetEntity );
                        }
                    }
                });
        //entityManager.getTransaction().commit();
    }

    private MALAssetEntity putIntoMALAssetMap(final MALGetAsset malGetAsset, final MALAssetType malAssetType) {
        final Optional<MALAssetEntity> optionalMALAssetEntity = malAssetRepository.findByAssetIdAndAssetType( malGetAsset.getAssetId(), malAssetType );
        MALAssetEntity malAssetEntity;
        if (optionalMALAssetEntity.isPresent()) {
            malAssetEntity = optionalMALAssetEntity.get();
            final String md5Hash = DigestUtils.md5Hex( GSON.toJson( malGetAsset ) );
            if( malAssetEntity.getMalLastModified().equals( malGetAsset.getLastModified() ) && md5Hash.equals( malAssetEntity.getMd5Hash() ) ) {
                return null;
            }
            malAssetEntity = malAssetModelsToMALAssetEntityTransformer.fromMALGetAssetUpdate( optionalMALAssetEntity.get(), malGetAsset, malAssetType);
        } else {
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


    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }
}
