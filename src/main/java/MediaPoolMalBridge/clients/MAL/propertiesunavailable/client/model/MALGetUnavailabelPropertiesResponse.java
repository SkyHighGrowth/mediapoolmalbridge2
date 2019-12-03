package MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.MALGetUnavailablePropertiesClient}
 */
public class MALGetUnavailabelPropertiesResponse extends MALAbstractResponse {

    /**
     * List of {@link Property} objects
     */
    private List<Property> properties;

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
