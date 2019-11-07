package MediaPoolMalBridge.clients.MAL.propertyphotos.client.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class MALGetPropertyPhotosRequest {

    //The property to list the assets for
    private String propertyId;

    //The marsha code to list the assets for
    private String marshaCode;

    //Boolean for including assets related to property ID
    private boolean includeRelatedAssets;

    public MultiValueMap<String, String> transformToGetParams()
    {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if(StringUtils.isNotBlank( propertyId ) ) {
            params.set( "property_id", propertyId );
        }
        if( StringUtils.isNotBlank( marshaCode ) ) {
            params.set( "marsha_code", marshaCode );
        }
        if( includeRelatedAssets ) {
            params.set( "include_related_assets", "1" );
        }
        return params;
    }
}
