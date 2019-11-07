package MediaPoolMalBridge.clients.MAL.state.client.model;

import com.google.gson.annotations.SerializedName;

public class State {

    @SerializedName( "state_id" )
    private String stateId;

    private String name;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
