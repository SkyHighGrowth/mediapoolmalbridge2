package MediaPoolMalBridge.clients.MAL.country.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.country.client.MALGetCountryClient}
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
