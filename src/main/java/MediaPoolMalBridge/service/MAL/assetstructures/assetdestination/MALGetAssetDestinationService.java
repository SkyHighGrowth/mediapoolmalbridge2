package MediaPoolMalBridge.service.MAL.assetstructures.assetdestination;

import MediaPoolMalBridge.clients.MAL.destionations.client.MALGetDestinationsClient;
import MediaPoolMalBridge.clients.MAL.destionations.client.model.Destination;
import MediaPoolMalBridge.clients.MAL.destionations.client.model.MALGetDestinationsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MALGetAssetDestinationService extends AbstractMALService {

    private final MALGetDestinationsClient getDestinationsClient;

    private final MALAssetStructures malAssetStructures;

    public MALGetAssetDestinationService(final MALGetDestinationsClient getDestinationsClient,
                                         final MALAssetStructures malAssetStructures) {
        this.getDestinationsClient = getDestinationsClient;
        this.malAssetStructures = malAssetStructures;
    }

    public void download() {
        final RestResponse<MALGetDestinationsResponse> response = getDestinationsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getDestinations() == null) {
            final String message = String.format("Invalid Destination response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        malAssetStructures.setDestinations(response.getResponse()
                .getDestinations()
                .stream()
                .collect(Collectors.toMap(Destination::getDestinationId, Destination::getName)));
    }
}
