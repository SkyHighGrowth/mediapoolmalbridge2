package MediaPoolMalBridge.clients.MAL.state.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.state.client.MALGetStatesClient}
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
