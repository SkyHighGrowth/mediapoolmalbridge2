package com.brandmaker.mediapoolmalbridge.clients.mal.state.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.state.client.MALGetStatesClient;

import java.util.List;

/**
 * Response of {@link MALGetStatesClient}
 */
public class MALGetStatesResponse extends MALAbstractResponse {

    /**
     * List of {@link State} objects
     */
    private List<State> states;

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }
}
