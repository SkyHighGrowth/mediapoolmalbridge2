package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.assetdestination;

import com.brandmaker.mediapoolmalbridge.clients.mal.destionations.client.MALGetDestinationsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.destionations.client.model.Destination;
import com.brandmaker.mediapoolmalbridge.clients.mal.destionations.client.model.MALGetDestinationsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.StructuresEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.StructureType;
import com.brandmaker.mediapoolmalbridge.service.StructuresService;
import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service that collects destionations from MAL server
 */
@Service
public class MALGetAssetDestinationUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetDestinationsClient getDestinationsClient;

    private final StructuresService structuresService;

    public MALGetAssetDestinationUniqueThreadService(final MALGetDestinationsClient getDestinationsClient,
                                                     final StructuresService structuresService) {
        this.getDestinationsClient = getDestinationsClient;
        this.structuresService = structuresService;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetDestinationsResponse> response = getDestinationsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getDestinations() == null) {
            final String message = String.format("Invalid Destination response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message);
            return;
        }
        List<StructuresEntity> structures = new ArrayList<>();
        for (Destination destination : response.getResponse().getDestinations()) {
            StructuresEntity entity = new StructuresEntity();
            entity.setStructureId(destination.getDestinationId());
            entity.setStructureName(destination.getName());
            entity.setStructureType(StructureType.DESTINATION);
            structures.add(entity);
        }
        structuresService.saveStructures(structures);

    }
}
