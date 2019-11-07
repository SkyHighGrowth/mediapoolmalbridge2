package MediaPoolMalBridge.clients.MAL.kits.client.model;

import com.google.gson.annotations.SerializedName;

public class Kit {

    @SerializedName( "kit_id" )
    private String kitId;

    private String name;

    public String getKitId() {
        return kitId;
    }

    public void setKitId(String kitId) {
        this.kitId = kitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
