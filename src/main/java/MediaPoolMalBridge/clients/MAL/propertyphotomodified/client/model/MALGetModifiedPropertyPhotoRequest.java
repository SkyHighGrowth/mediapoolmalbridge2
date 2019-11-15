package MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class MALGetModifiedPropertyPhotoRequest {

    //The property to list the assets for
    private String propertyId = null;

    //The marsha code to list the assets for
    private String marshaCode = null;

    //The date to restrict asset changes back until
    private String modifiedSince = null;

    //Boolean for including assets related to property ID
    private boolean includeRelatedAssets = false;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getMarshaCode() {
        return marshaCode;
    }

    public void setMarshaCode(String marshaCode) {
        this.marshaCode = marshaCode;
    }

    public String getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

    public boolean isIncludeRelatedAssets() {
        return includeRelatedAssets;
    }

    public void setIncludeRelatedAssets(boolean includeRelatedAssets) {
        this.includeRelatedAssets = includeRelatedAssets;
    }

    public MultiValueMap<String, String> transformToGetParams() {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (StringUtils.isNotBlank(propertyId)) {
            params.set("property_id", propertyId);
        }
        if (StringUtils.isNotBlank(marshaCode)) {
            params.set("marsha_code", marshaCode);
        }
        if (StringUtils.isNotBlank(modifiedSince)) {
            params.set("modified_since", modifiedSince);
        }
        if (includeRelatedAssets) {
            params.set("include_related_assets", "1");
        }
        return params;
    }
}
