package MediaPoolMalBridge.service.MAL.assetstructures.assetdestination;

import MediaPoolMalBridge.clients.MAL.destionations.client.MALGetDestinationsClient;
import MediaPoolMalBridge.clients.MAL.destionations.client.model.Destination;
import MediaPoolMalBridge.clients.MAL.destionations.client.model.MALGetDestinationsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service that collects destionations from MAL server
 */
@Service
public class MALGetAssetDestinationUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetDestinationsClient getDestinationsClient;

    private final MALAssetStructures assetStructures;

    public MALGetAssetDestinationUniqueThreadService(final MALGetDestinationsClient getDestinationsClient,
                                                     final MALAssetStructures assetStructures) {
        this.getDestinationsClient = getDestinationsClient;
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetDestinationsResponse> response = getDestinationsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getDestinations() == null) {
            final String message = String.format("Invalid Destination response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        assetStructures.setDestinations(response.getResponse()
                .getDestinations()
                .stream()
                .collect(Collectors.toMap(Destination::getDestinationId, Destination::getName)));
    }
}
