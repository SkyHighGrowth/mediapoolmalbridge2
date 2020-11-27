package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.propertytypes;

import com.brandmaker.mediapoolmalbridge.clients.mal.propertytypes.client.MALGetPropertyTypesClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertytypes.client.model.MALGetPropertyTypesResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertytypes.client.model.MALPropertyType;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import com.brandmaker.mediapoolmalbridge.model.mal.MALAssetStructures;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MALGetPropertyTypesUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetPropertyTypesClient getPropertyTypesClient;

    private final MALAssetStructures assetStructures;

    public MALGetPropertyTypesUniqueThreadService(final MALGetPropertyTypesClient getPropertyTypesClient,
                                                  final MALAssetStructures assetStructures) {
        this.getPropertyTypesClient = getPropertyTypesClient;
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetPropertyTypesResponse> response = getPropertyTypesClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getPropertyTypes() == null) {
            final String message = String.format("Invalid Property Types response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        assetStructures.setPropertyTypes(response.getResponse()
                .getPropertyTypes()
                .stream()
                .collect(Collectors.toMap(MALPropertyType::getPropertyTypeId, MALPropertyType::getName)));
    }
}
