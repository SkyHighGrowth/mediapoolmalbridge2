package MediaPoolMalBridge.clients.MAL.properties.client.model;

import java.util.ArrayList;
import java.util.List;

public class MALGetPropertiesResponse {

    private String result;

    private String message;

    private List<MALProperty> properties;

    public MALGetPropertiesResponse() {

    }

    public MALGetPropertiesResponse(final String result, final String message) {
        this.result = result;
        this.message = message;
        this.properties = new ArrayList<>();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MALProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<MALProperty> properties) {
        this.properties = properties;
    }
}
