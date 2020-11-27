package com.brandmaker.mediapoolmalbridge.service.mal.properties.deleted;

import com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client.MALGetUnavailablePropertiesClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client.model.MALGetUnavailabelPropertiesResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client.model.MALGetUnavailablePropertiesRequest;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.mal.MALPropertyEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.property.MALPropertyStatus;
import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for collecting deleted properties from MAL server
 */
@Service
public class MALGetPropertiesDeletedUniqueThreadSinceService extends AbstractMALUniqueThreadService {

    private final MALGetUnavailablePropertiesClient getUnavailablePropertiesClient;

    private String unavailableSince;

    public MALGetPropertiesDeletedUniqueThreadSinceService(final MALGetUnavailablePropertiesClient getUnavailablePropertiesClient) {
        this.getUnavailablePropertiesClient = getUnavailablePropertiesClient;
    }

    @Override
    protected void run() {
        final MALGetUnavailablePropertiesRequest request = new MALGetUnavailablePropertiesRequest();
        request.setUnavailableSince(unavailableSince);
        final RestResponse<MALGetUnavailabelPropertiesResponse> response = getUnavailablePropertiesClient.download(request);

        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getProperties() == null) {
            return;
        }

        response.getResponse().getProperties().forEach(property -> {
            final Optional<MALPropertyEntity> optionalMALPropertyEntity = malPropertyRepository.findByPropertyIdAndUpdatedIsAfter(property.getPropertyId(), getMidnightBridgeLookInThePast());
            if (!optionalMALPropertyEntity.isPresent()) {
                return;
            }
            final MALPropertyEntity malPropertyEntity = optionalMALPropertyEntity.get();
            malPropertyEntity.setMalPropertyStatus(MALPropertyStatus.DELETED );
            malPropertyRepository.save(malPropertyEntity);
        });
    }

    public String getUnavailableSince() {
        return unavailableSince;
    }

    public void setUnavailableSince(String unavailableSince) {
        this.unavailableSince = unavailableSince;
    }
}
