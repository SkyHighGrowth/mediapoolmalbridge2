package MediaPoolMalBridge.service.MAL.assetstructures.assetbrand;

import MediaPoolMalBridge.clients.MAL.brands.client.MALGetBrandsClient;
import MediaPoolMalBridge.clients.MAL.brands.client.model.Brand;
import MediaPoolMalBridge.clients.MAL.brands.client.model.MALGetBrandsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MALGetAssetBrandService extends AbstractMALService {

    private final MALGetBrandsClient getBrandsClient;

    private final MALAssetStructures malAssetStructures;

    public MALGetAssetBrandService(final MALGetBrandsClient getBrandsClient,
                                   final MALAssetStructures malAssetStructures) {
        this.getBrandsClient = getBrandsClient;
        this.malAssetStructures = malAssetStructures;
    }

    public void download() {
        final RestResponse<MALGetBrandsResponse> response = getBrandsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getBrands() == null) {
            final String message = String.format("Invalid Brands response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        malAssetStructures.setBrands(response.getResponse()
                .getBrands()
                .stream()
                .collect(Collectors.toMap(Brand::getBrandId, Brand::getName)));
    }
}
