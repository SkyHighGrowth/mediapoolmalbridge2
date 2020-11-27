package com.brandmaker.mediapoolmalbridge.clients.mal.propertieswithrelatedassets.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertieswithrelatedassets.client.MALGetPropertiesWithRelatedAssetsClient;

import java.util.List;

/**
 * Response of {@link MALGetPropertiesWithRelatedAssetsClient]}
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
