package MediaPoolMalBridge.service.MAL.assetstructures.assetfiletypes;

import MediaPoolMalBridge.clients.MAL.filetypes.client.MALGetFileTypesClient;
import MediaPoolMalBridge.clients.MAL.filetypes.client.model.FileType;
import MediaPoolMalBridge.clients.MAL.filetypes.client.model.MALGetFileTypesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MALGetAssetFileTypesService extends AbstractMALService {

    private final MALGetFileTypesClient getFileTypesClient;

    private final MALAssetStructures malAssetStructures;

    public MALGetAssetFileTypesService(final MALGetFileTypesClient getFileTypesClient,
                                       final MALAssetStructures malAssetStructures) {
        this.getFileTypesClient = getFileTypesClient;
        this.malAssetStructures = malAssetStructures;
    }

    public void download() {
        final RestResponse<MALGetFileTypesResponse> response = getFileTypesClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getFileTypes() == null) {
            final String message = String.format("Invalid File Types response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        malAssetStructures.setFileTypes(response.getResponse()
                .getFileTypes()
                .stream()
                .collect(Collectors.toMap(FileType::getFileTypeId, x -> x.getName().toLowerCase())));
    }
}
