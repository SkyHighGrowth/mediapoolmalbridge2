package com.brandmaker.mediapoolmalbridge.clients.mal.colors.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of {@link MALGetColorsResponse}
 */
public class Color {

    @SerializedName("color_id")
    private String colorId;

    private String name;

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
