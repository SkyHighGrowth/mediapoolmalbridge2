package com.brandmaker.mediapoolmalbridge.clients.mal.destionations.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.destionations.client.MALGetDestinationsClient;

import java.util.List;

/**
 * Response of {@link MALGetDestinationsClient}
 */
public class MALGetDestinationsResponse extends MALAbstractResponse {

    /**
     * List of {@link Destination} objects
     */
    private List<Destination> destinations;

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }
}
