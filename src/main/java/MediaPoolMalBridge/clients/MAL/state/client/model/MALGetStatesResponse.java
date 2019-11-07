package MediaPoolMalBridge.clients.MAL.state.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

public class MALGetStatesResponse extends MALAbstractResponse {

    private List<State> states;

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }
}
