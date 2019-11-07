package MediaPoolMalBridge.clients.MAL.collections.client.model;

import com.google.gson.annotations.SerializedName;

public class Collection {

    @SerializedName( "collection_id" )
    private String collectionId;

    private String name;

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
