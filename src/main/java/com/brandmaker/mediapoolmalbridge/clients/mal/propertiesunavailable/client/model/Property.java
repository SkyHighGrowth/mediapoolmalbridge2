package com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of {@link MALGetUnavailabelPropertiesResponse}
 */
public class Property {

    @SerializedName("property_id")
    private String propertyId;

    @SerializedName("unavailable_date")
    private String unavailableDate;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getUnavailableDate() {
        return unavailableDate;
    }

    public void setUnavailableDate(String unavailableDate) {
        this.unavailableDate = unavailableDate;
    }
}
