package com.brandmaker.mediapoolmalbridge.clients.mal.kits.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.kits.client.MALGetKitsClient;

import java.util.List;

/**
 * Response of {@link MALGetKitsClient}
 */
public class MALGetKitsResponse extends MALAbstractResponse {

    /**
     * List of {@link MALGetKit} objects
     */
    private List<MALGetKit> kits;

    public List<MALGetKit> getKits() {
        return kits;
    }

    public void setKits(List<MALGetKit> kits) {
        this.kits = kits;
    }
}
