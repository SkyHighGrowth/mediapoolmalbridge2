package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.assetfiletypes;

import com.brandmaker.mediapoolmalbridge.clients.mal.filetypes.client.MALGetFileTypesClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.filetypes.client.model.FileType;
import com.brandmaker.mediapoolmalbridge.clients.mal.filetypes.client.model.MALGetFileTypesResponse;
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
 * Service that collects file types from MAL service
 */
@Service
public class MALGetAssetFileTypesUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetFileTypesClient getFileTypesClient;

    private final StructuresService structuresService;

    public MALGetAssetFileTypesUniqueThreadService(final MALGetFileTypesClient getFileTypesClient,
                                                   final StructuresService structuresService) {
        this.getFileTypesClient = getFileTypesClient;
        this.structuresService = structuresService;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetFileTypesResponse> response = getFileTypesClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getFileTypes() == null) {
            final String message = String.format("Invalid File Types response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message);
            return;
        }
        List<StructuresEntity> structures = new ArrayList<>();
        for (FileType fileType : response.getResponse().getFileTypes()) {
            StructuresEntity entity = new StructuresEntity();
            entity.setStructureId(fileType.getFileTypeId());
            entity.setStructureName(fileType.getName());
            entity.setStructureType(StructureType.FILETYPE);
            structures.add(entity);
        }
        structuresService.saveStructures(structures);
    }
}
