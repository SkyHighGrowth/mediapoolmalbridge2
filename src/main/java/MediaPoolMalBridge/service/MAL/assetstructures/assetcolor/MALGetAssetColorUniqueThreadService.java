package MediaPoolMalBridge.service.MAL.assetstructures.assetcolor;

import MediaPoolMalBridge.clients.MAL.colors.client.MALGetColorsClient;
import MediaPoolMalBridge.clients.MAL.colors.client.model.Color;
import MediaPoolMalBridge.clients.MAL.colors.client.model.MALGetColorsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service that collects colors from MAL server
 */
@Service
public class MALGetAssetColorUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetColorsClient getColorsClient;

    private final MALAssetStructures assetStructures;

    public MALGetAssetColorUniqueThreadService(final MALGetColorsClient getColorsClient,
                                               final MALAssetStructures assetStructures) {
        this.getColorsClient = getColorsClient;
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetColorsResponse> response = getColorsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getColors() == null) {
            final String message = String.format("Invalid Collors response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        assetStructures.setColors(response.getResponse()
                .getColors()
                .stream()
                .collect(Collectors.toMap(Color::getColorId, Color::getName)));
    }
}
