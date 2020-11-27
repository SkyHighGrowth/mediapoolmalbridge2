package com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client.MALGetUnavailablePropertiesClient;

import java.util.List;

/**
 * Response of {@link MALGetUnavailablePropertiesClient}
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
