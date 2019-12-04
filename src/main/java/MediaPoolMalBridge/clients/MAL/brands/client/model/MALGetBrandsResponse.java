package MediaPoolMalBridge.clients.MAL.brands.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Class represents response of {@link MediaPoolMalBridge.clients.MAL.brands.client.MALGetBrandsClient}
 */
public class MALGetBrandsResponse extends MALAbstractResponse {

    /**
     * List of {@link Brand} objects
     */
    private List<Brand> brands;

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
