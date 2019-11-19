package MediaPoolMalBridge.service.MAL.kits;

import MediaPoolMalBridge.clients.MAL.kits.client.MALGetKitsClient;
import MediaPoolMalBridge.clients.MAL.kits.client.model.MALGetKitsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.kits.MALKits;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import org.springframework.stereotype.Service;

@Service
public class MALGetKitsService extends AbstractMALService {

    private final MALGetKitsClient getKitsClient;

    private final MALKits malKits;

    public MALGetKitsService(final MALGetKitsClient getKitsClient,
                             final MALKits malKits) {
        this.getKitsClient = getKitsClient;
        this.malKits = malKits;
    }

    public void downloadKits() {
        final RestResponse<MALGetKitsResponse> response = getKitsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getKits() == null) {
            final String message = String.format("Invalid Kits response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        response.getResponse().getKits().forEach(malKit -> malKits.put(malKit.getName(), malKit.getKitId()));
    }
}
