package com.brandmaker.mediapoolmalbridge.clients.mal.colors.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.colors.client.MALGetColorsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MALGetColorsClient}
 */
public class MALGetColorsResponse extends MALAbstractResponse {

    /**
     * List of {@link Color} objects
     */
    private List<Color> colors;

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }
}
