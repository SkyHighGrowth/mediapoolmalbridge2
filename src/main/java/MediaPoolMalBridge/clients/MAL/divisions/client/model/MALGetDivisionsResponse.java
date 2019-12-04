package MediaPoolMalBridge.clients.MAL.divisions.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.divisions.client.MALGetDivisionsClient}
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
