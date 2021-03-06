package com.brandmaker.mediapoolmalbridge.clients.mal.propertytypes.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of {@link MALGetPropertyTypesResponse}
 */
public class MALPropertyType {

    @SerializedName("property_type_id")
    private String propertyTypeId;

    private String name;

    public String getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(String propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
