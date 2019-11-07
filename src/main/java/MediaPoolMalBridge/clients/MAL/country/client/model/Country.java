package MediaPoolMalBridge.clients.MAL.country.client.model;

import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName( "country_id" )
    private String countryId;

    @SerializedName( "division_id" )
    private String divisionId;

    private String name;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

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
