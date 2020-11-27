package com.brandmaker.mediapoolmalbridge.service.mal.kits;

import com.brandmaker.mediapoolmalbridge.clients.mal.kits.client.MALGetKitsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.kits.client.model.MALGetKitsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import com.brandmaker.mediapoolmalbridge.model.mal.kits.MALKits;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service that downloads kits from MAL server
 */
@Service
public class MALGetKitsUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetKitsClient getKitsClient;

    private final MALKits malKits;

    public MALGetKitsUniqueThreadService(final MALGetKitsClient getKitsClient,
                                         final MALKits malKits) {
        this.getKitsClient = getKitsClient;
        this.malKits = malKits;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetKitsResponse> response = getKitsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getKits() == null) {
            final String message = String.format("Invalid Kits response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        response.getResponse().getKits().forEach(malKit -> malKits.put(malKit.getName(), malKit.getKitId()));
    }
}
