package MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

public class MALGetUnavailabelPropertiesResponse extends MALAbstractResponse {

    private List<Property> properties;

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
