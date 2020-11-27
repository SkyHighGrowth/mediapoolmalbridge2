package com.brandmaker.mediapoolmalbridge.clients.mal.country.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.country.client.MALGetCountryClient;

import java.util.List;

/**
 * Response of {@link MALGetCountryClient}
 */
public class MALGetCountryResponse extends MALAbstractResponse {

    /**
     * List of {@link Country} objects
     */
    private List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
