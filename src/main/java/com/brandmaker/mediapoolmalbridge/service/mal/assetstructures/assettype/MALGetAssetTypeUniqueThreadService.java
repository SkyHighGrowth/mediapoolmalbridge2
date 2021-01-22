package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.assettype;

import com.brandmaker.mediapoolmalbridge.clients.mal.assettypes.client.MALGetAssetTypesClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.assettypes.client.model.AssetType;
import com.brandmaker.mediapoolmalbridge.clients.mal.assettypes.client.model.MALGetAssetTypesResponse;
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
 * Service that collects asset types from MAL service
 */
@Service
public class MALGetAssetTypeUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetAssetTypesClient getAssetTypesClient;

    private final StructuresService structuresService;

    public MALGetAssetTypeUniqueThreadService(final MALGetAssetTypesClient getAssetTypesClient,
                                              final StructuresService structuresService) {
        this.getAssetTypesClient = getAssetTypesClient;
        this.structuresService = structuresService;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetAssetTypesResponse> response = getAssetTypesClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getAssetTypes() == null) {
            final String message = String.format("Invalid Asset Types response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message);
            return;
        }
        List<StructuresEntity> structures = new ArrayList<>();
        for (AssetType assetType : response.getResponse().getAssetTypes()) {
            StructuresEntity entity = new StructuresEntity();
            entity.setStructureId(assetType.getAssetTypeId());
            entity.setStructureName(assetType.getName());
            entity.setStructureType(StructureType.ASSETTYPE);
            structures.add(entity);
        }
        structuresService.saveStructures(structures);
    }
}
