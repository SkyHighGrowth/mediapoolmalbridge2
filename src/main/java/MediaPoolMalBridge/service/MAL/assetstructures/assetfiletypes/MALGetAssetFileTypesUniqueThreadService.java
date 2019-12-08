package MediaPoolMalBridge.service.MAL.assetstructures.assetfiletypes;

import MediaPoolMalBridge.clients.MAL.filetypes.client.MALGetFileTypesClient;
import MediaPoolMalBridge.clients.MAL.filetypes.client.model.FileType;
import MediaPoolMalBridge.clients.MAL.filetypes.client.model.MALGetFileTypesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service that collects file types from MAL service
 */
@Service
public class MALGetAssetFileTypesUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetFileTypesClient getFileTypesClient;

    private final MALAssetStructures assetStructures;

    public MALGetAssetFileTypesUniqueThreadService(final MALGetFileTypesClient getFileTypesClient,
                                                   final MALAssetStructures assetStructures) {
        this.getFileTypesClient = getFileTypesClient;
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetFileTypesResponse> response = getFileTypesClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getFileTypes() == null) {
            final String message = String.format("Invalid File Types response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        assetStructures.setFileTypes(response.getResponse()
                .getFileTypes()
                .stream()
                .collect(Collectors.toMap(FileType::getFileTypeId, x -> x.getName().toLowerCase())));
    }
}
