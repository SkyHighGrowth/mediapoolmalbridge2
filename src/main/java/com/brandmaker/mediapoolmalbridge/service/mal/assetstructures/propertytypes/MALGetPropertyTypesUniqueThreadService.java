package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.propertytypes;

import com.brandmaker.mediapoolmalbridge.clients.mal.propertytypes.client.MALGetPropertyTypesClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertytypes.client.model.MALGetPropertyTypesResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertytypes.client.model.MALPropertyType;
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

@Service
public class MALGetPropertyTypesUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetPropertyTypesClient getPropertyTypesClient;

    private final StructuresService structuresService;

    public MALGetPropertyTypesUniqueThreadService(final MALGetPropertyTypesClient getPropertyTypesClient,
                                                  final StructuresService structuresService) {
        this.getPropertyTypesClient = getPropertyTypesClient;
        this.structuresService = structuresService;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetPropertyTypesResponse> response = getPropertyTypesClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getPropertyTypes() == null) {
            final String message = String.format("Invalid Property Types response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message);
            return;
        }
        List<StructuresEntity> structures = new ArrayList<>();
        for (MALPropertyType malPropertyType : response.getResponse().getPropertyTypes()) {
            StructuresEntity entity = new StructuresEntity();
            entity.setStructureId(malPropertyType.getPropertyTypeId());
            entity.setStructureName(malPropertyType.getName());
            entity.setStructureType(StructureType.PROPERTYTYPE);
            structures.add(entity);
        }
        structuresService.saveStructures(structures);
    }
}
