package MediaPoolMalBridge.service.MAL.properties.deleted;

import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.MALGetUnavailablePropertiesClient;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model.MALGetUnavailabelPropertiesResponse;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model.MALGetUnavailablePropertiesRequest;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MALGetPropertiesDeletedService extends AbstractMALUniqueService {

    private final MALGetUnavailablePropertiesClient getUnavailablePropertiesClient;

    private String unavailableSince;

    public MALGetPropertiesDeletedService(final MALGetUnavailablePropertiesClient getUnavailablePropertiesClient) {
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
            final Optional<MALPropertyEntity> optionalMALPropertyEntity = malPropertyRepository.findByPropertyId(property.getPropertyId());
            if (!optionalMALPropertyEntity.isPresent()) {
                return;
            }
            final MALPropertyEntity malPropertyEntity = optionalMALPropertyEntity.get();
            malPropertyEntity.setStatus(0);
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
