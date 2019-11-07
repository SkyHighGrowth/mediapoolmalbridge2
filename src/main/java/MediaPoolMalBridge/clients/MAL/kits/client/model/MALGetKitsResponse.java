package MediaPoolMalBridge.clients.MAL.kits.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

public class MALGetKitsResponse extends MALAbstractResponse {

    private List<Kit> kits;

    public List<Kit> getKits() {
        return kits;
    }

    public void setKits(List<Kit> kits) {
        this.kits = kits;
    }
}
