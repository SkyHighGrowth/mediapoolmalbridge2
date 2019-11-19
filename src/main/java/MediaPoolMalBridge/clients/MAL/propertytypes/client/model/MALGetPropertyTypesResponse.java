package MediaPoolMalBridge.clients.MAL.propertytypes.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MALGetPropertyTypesResponse extends MALAbstractResponse {

    @SerializedName("property_types")
    private List<MALPropertyType> propertyTypes;

    public List<MALPropertyType> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(List<MALPropertyType> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }
}
