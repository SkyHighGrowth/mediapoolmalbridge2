package com.brandmaker.mediapoolmalbridge.clients.mal.divisions.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.divisions.client.MALGetDivisionsClient;

import java.util.List;

/**
 * Response of {@link MALGetDivisionsClient}
 */
public class MALGetDivisionsResponse extends MALAbstractResponse {

    /**
     * List of {@link Division} objects
     */
    private List<Division> divisions;

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }
}
