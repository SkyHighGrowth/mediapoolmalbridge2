package MediaPoolMalBridge.service.MAL.assetstructures.assettype;

import MediaPoolMalBridge.clients.MAL.assettypes.client.MALGetAssetTypesClient;
import MediaPoolMalBridge.clients.MAL.assettypes.client.model.AssetType;
import MediaPoolMalBridge.clients.MAL.assettypes.client.model.MALGetAssetTypesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MALGetAssetTypeService extends AbstractMALService {

    private final MALGetAssetTypesClient getAssetTypesClient;

    private final MALAssetStructures malAssetStructures;

    public MALGetAssetTypeService(final MALGetAssetTypesClient getAssetTypesClient,
                                  final MALAssetStructures malAssetStructures) {
        this.getAssetTypesClient = getAssetTypesClient;
        this.malAssetStructures = malAssetStructures;
    }

    public void download() {
        final RestResponse<MALGetAssetTypesResponse> response = getAssetTypesClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getAssetTypes() == null) {
            final String message = String.format("Invalid Asset Types response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        malAssetStructures.setAssetTypes(response.getResponse()
                .getAssetTypes()
                .stream()
                .collect(Collectors.toMap(AssetType::getAssetTypeId, AssetType::getName)));
    }
}
