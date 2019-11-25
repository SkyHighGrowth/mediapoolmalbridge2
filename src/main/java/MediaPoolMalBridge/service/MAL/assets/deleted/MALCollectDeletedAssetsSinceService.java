package MediaPoolMalBridge.service.MAL.assets.deleted;

import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.MALGetUnavailableAssetsClient;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAsset;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsRequest;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsResponse;
import MediaPoolMalBridge.clients.MAL.model.MALAssetType;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class MALCollectDeletedAssetsSinceService extends AbstractMALUniqueService {

    private final MALGetUnavailableAssetsClient getUnavailableAssetsClient;

    private String since;

    @PersistenceContext
    private EntityManager entityManager;

    public MALCollectDeletedAssetsSinceService(final MALGetUnavailableAssetsClient getUnavailableAssetsClient) {
        this.getUnavailableAssetsClient = getUnavailableAssetsClient;
    }

    @Override
    protected void run() {
        final MALGetUnavailableAssetsRequest request = new MALGetUnavailableAssetsRequest();
        request.setUnavailableSince(since);

        final RestResponse<MALGetUnavailableAssetsResponse> response = getUnavailableAssetsClient.download(request);

        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getAssets() == null) {
            final String message = String.format("Can not download list of unavailable assets since [%s] with response [%s]",
                    since,
                    GSON.toJson(response.getResponse()));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        entityManager.getTransaction().begin();
        response.getResponse()
                .getAssets()
                .forEach(malGetUnavailableAsset -> {
                    addAssetToDelete(malGetUnavailableAsset, MALAssetType.FILE);
                    addAssetToDelete(malGetUnavailableAsset, MALAssetType.JPG_LOGO);
                    addAssetToDelete(malGetUnavailableAsset, MALAssetType.PNG_LOGO);
                });
        entityManager.getTransaction().commit();
    }

    private void addAssetToDelete(final MALGetUnavailableAsset malGetUnavailableAsset, final MALAssetType malAssetType) {
        final Optional<MALAssetEntity> optionalMalAssetEntity = malAssetRepository.findByAssetIdAndAssetType(malGetUnavailableAsset.getAssetId(), malAssetType);
        if (!optionalMalAssetEntity.isPresent()) {
            return;
        }
        final MALAssetEntity malAssetEntity = optionalMalAssetEntity.get();
        malAssetEntity.setTransferringAssetStatus(TransferringAssetStatus.MAL_DELETED);
        malAssetEntity.setBmAssetId(malAssetEntity.getBmAssetId());
        //malAssetRepository.save(malAssetEntity);
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }
}
