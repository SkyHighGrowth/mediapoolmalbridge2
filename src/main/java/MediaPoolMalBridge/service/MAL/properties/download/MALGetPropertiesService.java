package MediaPoolMalBridge.service.MAL.properties.download;

import MediaPoolMalBridge.clients.MAL.properties.client.MALGetPropertiesClient;
import MediaPoolMalBridge.clients.MAL.properties.client.model.MALGetPropertiesRequest;
import MediaPoolMalBridge.clients.MAL.properties.client.model.MALGetPropertiesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.persistence.entity.MAL.MALPropertyEntity;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MALGetPropertiesService extends AbstractMALUniqueService {

    private final MALGetPropertiesClient getPropertiesClient;

    public MALGetPropertiesService(final MALGetPropertiesClient getPropertiesClient) {
        this.getPropertiesClient = getPropertiesClient;
    }

    @Override
    protected void run() {
        final MALGetPropertiesRequest request = new MALGetPropertiesRequest();
        request.setPerPage(200);
        request.setPage(1);
        final RestResponse<MALGetPropertiesResponse> response = getPropertiesClient.download(request);
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getProperties() == null ||
                StringUtils.isBlank(response.getResponse().getTotalPages())) {
            return;
        }

        final int totalPages;
        try {
            totalPages = Integer.parseInt(response.getResponse().getTotalPages());
        } catch (final Exception e) {
            final String message = String.format("Can not parse totalPages of properties with httpStatus [%s], and responseBody [%s]",
                    response.getHttpStatus(),
                    GSON.toJson(response.getResponse()));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        transformPagesIntoProperties(request, totalPages);
    }

    private void transformPagesIntoProperties(final MALGetPropertiesRequest request, final int totalPages) {
        for (int page = 0; page < totalPages; ++page) {
            request.setPage(page + 1);
            final RestResponse<MALGetPropertiesResponse> response = getPropertiesClient.download(request);
            if (!response.isSuccess() ||
                    response.getResponse() == null ||
                    response.getResponse().getProperties() == null ||
                    StringUtils.isBlank(response.getResponse().getTotalPages())) {
                continue;
            }
            response.getResponse()
                    .getProperties()
                    .forEach(malProperty -> {
                        final Optional<MALPropertyEntity> optionalMALPropertyEntity = malPropertyRepository.findByPropertyId(malProperty.getPropertyId());
                        if (!optionalMALPropertyEntity.isPresent()) {
                            final MALPropertyEntity malPropertyEntity = new MALPropertyEntity(malProperty);
                            malPropertyRepository.save(malPropertyEntity);
                            return;
                        }
                        final MALPropertyEntity malPropertyEntity = optionalMALPropertyEntity.get();
                        malPropertyEntity.update(malProperty);
                        malPropertyRepository.save(malPropertyEntity);
                    });
        }
    }
}
