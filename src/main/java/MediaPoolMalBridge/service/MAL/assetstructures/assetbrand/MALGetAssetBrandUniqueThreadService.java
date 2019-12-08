package MediaPoolMalBridge.service.MAL.assetstructures.assetbrand;

import MediaPoolMalBridge.clients.MAL.brands.client.MALGetBrandsClient;
import MediaPoolMalBridge.clients.MAL.brands.client.model.Brand;
import MediaPoolMalBridge.clients.MAL.brands.client.model.MALGetBrandsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service that downloads asset brands form MAL server
 */
@Service
public class MALGetAssetBrandUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetBrandsClient getBrandsClient;

    private final MALAssetStructures assetStructures;

    public MALGetAssetBrandUniqueThreadService(final MALGetBrandsClient getBrandsClient,
                                               final MALAssetStructures assetStructures) {
        this.getBrandsClient = getBrandsClient;
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetBrandsResponse> response = getBrandsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getBrands() == null) {
            final String message = String.format("Invalid Brands response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        assetStructures.setBrands(response.getResponse()
                .getBrands()
                .stream()
                .collect(Collectors.toMap(Brand::getBrandId, Brand::getName)));
    }
}
