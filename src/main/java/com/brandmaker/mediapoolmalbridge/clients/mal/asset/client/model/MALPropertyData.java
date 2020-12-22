package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for fetching asset property data from api/syncs
 */
public class MALPropertyData {
    @SerializedName("property_id")
    public String propertyId;
    @SerializedName("marsha_code")
    public String marshaCode;
    @SerializedName("name")
    public String name;
    @SerializedName("status")
    public String status;
    @SerializedName("is_active")
    public Boolean isActive;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getMarshaCode() {
        return marshaCode;
    }

    public void setMarshaCode(String marshaCode) {
        this.marshaCode = marshaCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
