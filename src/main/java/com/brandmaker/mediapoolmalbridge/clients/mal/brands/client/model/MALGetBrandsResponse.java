package com.brandmaker.mediapoolmalbridge.clients.mal.brands.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.brands.client.MALGetBrandsClient;

import java.util.List;

/**
 * Class represents response of {@link MALGetBrandsClient}
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
