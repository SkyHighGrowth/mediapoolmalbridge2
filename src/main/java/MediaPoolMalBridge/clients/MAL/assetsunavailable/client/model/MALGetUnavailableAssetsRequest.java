package MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Class that represents request for {@link MediaPoolMalBridge.clients.MAL.assetsunavailable.client.MALGetUnavailableAssetsClient}
 */
public class MALGetUnavailableAssetsRequest {

    /**
     * datetime point for which want unavailable assets
     */
    private String unavailableSince = null;

    public String getUnavailableSince() {
        return unavailableSince;
    }

    public void setUnavailableSince(String unavailableSince) {
        this.unavailableSince = unavailableSince;
    }

    /**
     * Class representation to get request parameters
     * @return
     */
    public MultiValueMap<String, String> transformToGetParams() {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (StringUtils.isNotBlank(unavailableSince)) {
            params.set("unavailable_since", unavailableSince);
        }
        return params;
    }
}
