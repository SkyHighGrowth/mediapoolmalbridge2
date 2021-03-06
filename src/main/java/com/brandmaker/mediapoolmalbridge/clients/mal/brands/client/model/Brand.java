package com.brandmaker.mediapoolmalbridge.clients.mal.brands.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of {@link MALGetBrandsResponse}
 */
public class Brand {

    @SerializedName("brand_id")
    private String brandId;

    private String name;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
