package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.assetcollection;

import com.brandmaker.mediapoolmalbridge.clients.mal.collections.client.MALGetCollectionsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.collections.client.model.Collection;
import com.brandmaker.mediapoolmalbridge.clients.mal.collections.client.model.MALGetCollectionsResponse;
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
 * Service that collects collections from MAL server
 */
@Service
public class MALGetAssetCollectionUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetCollectionsClient getCollectionsClient;

    private final StructuresService structuresService;

    public MALGetAssetCollectionUniqueThreadService(final MALGetCollectionsClient getCollectionsClient,
                                                    final StructuresService structuresService) {
        this.getCollectionsClient = getCollectionsClient;
        this.structuresService = structuresService;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetCollectionsResponse> response = getCollectionsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getCollections() == null) {
            final String message = String.format("Invalid Collections response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message);
            return;
        }
        List<StructuresEntity> structures = new ArrayList<>();
        for (Collection collection : response.getResponse().getCollections()) {
            StructuresEntity entity = new StructuresEntity();
            entity.setStructureId(collection.getCollectionId());
            entity.setStructureName(collection.getName());
            entity.setStructureType(StructureType.COLLECTION);
            structures.add(entity);
        }
        structuresService.saveStructures(structures);
    }
}
