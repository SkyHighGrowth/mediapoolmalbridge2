package MediaPoolMalBridge.clients.MAL.divisions.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

public class MALGetDivisionsResponse extends MALAbstractResponse {

    private List<Division> divisions;

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }
}
