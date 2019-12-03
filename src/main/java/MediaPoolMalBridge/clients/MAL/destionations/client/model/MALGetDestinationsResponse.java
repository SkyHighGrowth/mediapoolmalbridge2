package MediaPoolMalBridge.clients.MAL.destionations.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.destionations.client.MALGetDestinationsClient}
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
