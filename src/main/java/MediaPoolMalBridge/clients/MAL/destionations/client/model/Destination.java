package MediaPoolMalBridge.clients.MAL.destionations.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of {@link MALGetDestinationsResponse}
 */
public class Destination {

    @SerializedName("destination_id")
    private String destinationId;

    private String name;

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
