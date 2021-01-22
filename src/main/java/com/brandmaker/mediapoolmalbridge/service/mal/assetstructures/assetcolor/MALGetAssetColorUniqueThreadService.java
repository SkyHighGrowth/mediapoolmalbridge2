package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.assetcolor;

import com.brandmaker.mediapoolmalbridge.clients.mal.colors.client.MALGetColorsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.colors.client.model.Color;
import com.brandmaker.mediapoolmalbridge.clients.mal.colors.client.model.MALGetColorsResponse;
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
 * Service that collects colors from MAL server
 */
@Service
public class MALGetAssetColorUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetColorsClient getColorsClient;

    private final StructuresService structuresService;

    public MALGetAssetColorUniqueThreadService(final MALGetColorsClient getColorsClient,
                                               final StructuresService structuresService) {
        this.getColorsClient = getColorsClient;
        this.structuresService = structuresService;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetColorsResponse> response = getColorsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getColors() == null) {
            final String message = String.format("Invalid Collors response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message);
            return;
        }
        List<StructuresEntity> structures = new ArrayList<>();
        for (Color color : response.getResponse().getColors()) {
            StructuresEntity entity = new StructuresEntity();
            entity.setStructureId(color.getColorId());
            entity.setStructureName(color.getName());
            entity.setStructureType(StructureType.COLOR);
            structures.add(entity);
        }
        structuresService.saveStructures(structures);
    }
}
