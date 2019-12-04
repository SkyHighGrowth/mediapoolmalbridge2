package MediaPoolMalBridge.clients.MAL.kits.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.kits.client.MALGetKitsClient}
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
