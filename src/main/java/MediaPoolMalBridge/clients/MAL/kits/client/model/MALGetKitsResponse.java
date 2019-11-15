package MediaPoolMalBridge.clients.MAL.kits.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

public class MALGetKitsResponse extends MALAbstractResponse {

    private List<MALGetKit> kits;

    public List<MALGetKit> getKits() {
        return kits;
    }

    public void setKits(List<MALGetKit> kits) {
        this.kits = kits;
    }
}
