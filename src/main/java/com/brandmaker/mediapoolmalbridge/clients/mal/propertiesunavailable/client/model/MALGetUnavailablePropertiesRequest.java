package com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client.MALGetUnavailablePropertiesClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Request of {@link MALGetUnavailablePropertiesClient}
 */
public class MALGetUnavailablePropertiesRequest {

    private String unavailableSince = null;

    public String getUnavailableSince() {
        return unavailableSince;
    }

    public void setUnavailableSince(String unavailableSince) {
        this.unavailableSince = unavailableSince;
    }

    public MultiValueMap<String, String> transformToGetParams() {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (StringUtils.isNotBlank(unavailableSince)) {
            params.set("unavailable_since", unavailableSince);
        }
        return params;
    }
}
