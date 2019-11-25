package MediaPoolMalBridge.service.MAL.assetstructures.propertytypes;

import MediaPoolMalBridge.clients.MAL.propertytypes.client.MALGetPropertyTypesClient;
import MediaPoolMalBridge.clients.MAL.propertytypes.client.model.MALGetPropertyTypesResponse;
import MediaPoolMalBridge.clients.MAL.propertytypes.client.model.MALPropertyType;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MALGetPropertyTypesService extends AbstractMALUniqueService {

    private final MALGetPropertyTypesClient getPropertyTypesClient;

    private final MALAssetStructures malAssetStructures;

    public MALGetPropertyTypesService(final MALGetPropertyTypesClient getPropertyTypesClient,
                                      final MALAssetStructures malAssetStructures) {
        this.getPropertyTypesClient = getPropertyTypesClient;
        this.malAssetStructures = malAssetStructures;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetPropertyTypesResponse> response = getPropertyTypesClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getPropertyTypes() == null) {
            final String message = String.format("Invalid Property Types response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        malAssetStructures.setPropertyTypes(response.getResponse()
                .getPropertyTypes()
                .stream()
                .collect(Collectors.toMap(MALPropertyType::getPropertyTypeId, MALPropertyType::getName)));
    }
}
