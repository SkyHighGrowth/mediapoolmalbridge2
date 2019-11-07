package MediaPoolMalBridge.clients.MAL.propertytypes.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;
import com.google.gson.annotations.SerializedName;

public class MALGetPropertyTypesResponse extends MALAbstractResponse {

    @SerializedName( "property_type_id" )
    private String propertyTypeId;

    private String name;

    public String getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(String propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
