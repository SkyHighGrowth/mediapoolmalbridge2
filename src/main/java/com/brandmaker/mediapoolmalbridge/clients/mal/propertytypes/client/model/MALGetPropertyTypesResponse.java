package com.brandmaker.mediapoolmalbridge.clients.mal.propertytypes.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotos.client.MALGetPropertyPhotosClient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response of {@link MALGetPropertyPhotosClient}
 */
public class MALGetPropertyTypesResponse extends MALAbstractResponse {

    /**
     * List of {@link MALPropertyType} objects
     */
    @SerializedName("property_types")
    private List<MALPropertyType> propertyTypes;

    public List<MALPropertyType> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(List<MALPropertyType> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }
}
