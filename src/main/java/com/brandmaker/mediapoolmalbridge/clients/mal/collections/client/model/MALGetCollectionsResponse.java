package com.brandmaker.mediapoolmalbridge.clients.mal.collections.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.collections.client.MALGetCollectionsClient;

import java.util.List;

/**
 * Response of {@link MALGetCollectionsClient}
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
