package MediaPoolMalBridge.service.MAL.assetstructures.assetcollection;

import MediaPoolMalBridge.clients.MAL.collections.client.MALGetCollectionsClient;
import MediaPoolMalBridge.clients.MAL.collections.client.model.Collection;
import MediaPoolMalBridge.clients.MAL.collections.client.model.MALGetCollectionsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MALGetAssetCollectionUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetCollectionsClient getCollectionsClient;

    private final MALAssetStructures malAssetStructures;

    public MALGetAssetCollectionUniqueThreadService(final MALGetCollectionsClient getCollectionsClient,
                                                    final MALAssetStructures malAssetStructures) {
        this.getCollectionsClient = getCollectionsClient;
        this.malAssetStructures = malAssetStructures;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetCollectionsResponse> response = getCollectionsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getCollections() == null) {
            final String message = String.format("Invalid Collections response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        malAssetStructures.setCollections(response.getResponse()
                .getCollections()
                .stream()
                .collect(Collectors.toMap(Collection::getCollectionId, Collection::getName)));
    }
}
