package MediaPoolMalBridge.clients.MAL.propertieswithrelatedassets.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.propertieswithrelatedassets.client.MALGetPropertiesWithRelatedAssetsClient]}
 */
public class MALGetPropertiesWithRelatedAssetsResponse extends MALAbstractResponse {

    private List<String> properties;

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
}
