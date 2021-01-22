package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.assetbrand;

import com.brandmaker.mediapoolmalbridge.clients.mal.brands.client.MALGetBrandsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.brands.client.model.Brand;
import com.brandmaker.mediapoolmalbridge.clients.mal.brands.client.model.MALGetBrandsResponse;
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
 * Service that downloads asset brands form MAL server
 */
@Service
public class MALGetAssetBrandUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetBrandsClient getBrandsClient;

    private final StructuresService structuresService;

    public MALGetAssetBrandUniqueThreadService(final MALGetBrandsClient getBrandsClient,
                                               final StructuresService structuresService) {
        this.getBrandsClient = getBrandsClient;
        this.structuresService = structuresService;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetBrandsResponse> response = getBrandsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getBrands() == null) {
            final String message = String.format("Invalid Brands response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity(ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null);
            reportsRepository.save(reportsEntity);
            logger.error(message);
            return;
        }

        List<StructuresEntity> structures = new ArrayList<>();
        for (Brand brand : response.getResponse().getBrands()) {
            StructuresEntity entity = new StructuresEntity();
            entity.setStructureId(brand.getBrandId());
            entity.setStructureName(brand.getName());
            entity.setStructureType(StructureType.BRAND);
            structures.add(entity);
        }
        structuresService.saveStructures(structures);
    }
}
