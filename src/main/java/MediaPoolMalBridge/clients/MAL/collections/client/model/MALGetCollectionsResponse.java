package MediaPoolMalBridge.clients.MAL.collections.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.collections.client.MALGetCollectionsClient}
 */
public class MALGetCollectionsResponse extends MALAbstractResponse {

    /**
     * List of {@link Collection} objects
     */
    private List<Collection> collections;

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }
}
