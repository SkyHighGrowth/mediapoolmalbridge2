package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.assetcolor;

import com.brandmaker.mediapoolmalbridge.clients.mal.colors.client.MALGetColorsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.colors.client.model.Color;
import com.brandmaker.mediapoolmalbridge.clients.mal.colors.client.model.MALGetColorsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import com.brandmaker.mediapoolmalbridge.model.mal.MALAssetStructures;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALUniqueThreadService;
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
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null );
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
