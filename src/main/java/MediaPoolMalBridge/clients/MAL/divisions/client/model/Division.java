package MediaPoolMalBridge.clients.MAL.divisions.client.model;

import com.google.gson.annotations.SerializedName;

public class Division {

    @SerializedName("division_id")
    private String divisionId;

    private String name;

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
