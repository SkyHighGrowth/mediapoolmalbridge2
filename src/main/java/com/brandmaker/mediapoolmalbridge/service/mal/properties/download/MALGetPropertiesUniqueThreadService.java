package com.brandmaker.mediapoolmalbridge.service.mal.properties.download;

import com.brandmaker.mediapoolmalbridge.clients.mal.properties.client.MALGetPropertiesClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.properties.client.model.MALGetPropertiesRequest;
import com.brandmaker.mediapoolmalbridge.clients.mal.properties.client.model.MALGetPropertiesResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.properties.client.model.MALProperty;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.mal.MALPropertyEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.property.MALPropertyStatus;
import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALUniqueThreadService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service that collects properties from MAL server
 */
@Service
public class MALGetPropertiesUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetPropertiesClient getPropertiesClient;

    public MALGetPropertiesUniqueThreadService(final MALGetPropertiesClient getPropertiesClient) {
        this.getPropertiesClient = getPropertiesClient;
    }

    @Override
    protected void run() {
        final MALGetPropertiesRequest request = new MALGetPropertiesRequest();
        request.setPerPage(appConfig.getMalPageSize());
        request.setPage(0);
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
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error( message, e );
            return;
        }

        transformPagesIntoProperties(request, totalPages);
    }

    private void transformPagesIntoProperties(final MALGetPropertiesRequest request, final int totalPages) {
        for (int page = 0; page < totalPages; ++page) {
            request.setPage(page);
            final RestResponse<MALGetPropertiesResponse> response = getPropertiesClient.download(request);
            if (!response.isSuccess() ||
                    response.getResponse() == null ||
                    response.getResponse().getProperties() == null ) {
                continue;
            }
            transformPageIntoProperties( response.getResponse().getProperties() );
        }
    }

    private void transformPageIntoProperties(final List<MALProperty> malProperties )
    {
        malProperties.forEach(malProperty -> {
            final String propertyMd5Hash;
            try {
                propertyMd5Hash = DigestUtils.md5Hex(objectMapper.writeValueAsString(malProperty));
            } catch ( final Exception e ) {
                return;
            }
            final Optional<MALPropertyEntity> optionalMALPropertyEntity = malPropertyRepository.findByPropertyId(malProperty.getPropertyId());
            if (optionalMALPropertyEntity.isPresent()) {
                final MALPropertyEntity malPropertyEntity = optionalMALPropertyEntity.get();
                if( propertyMd5Hash.equals( malPropertyEntity.getMd5Hash() ) ) {
                    return;
                }
                malPropertyEntity.update( malProperty, propertyMd5Hash, MALPropertyStatus.OBSERVED );
                malPropertyRepository.save(malPropertyEntity);
            } else {
                final MALPropertyEntity malPropertyEntity = new MALPropertyEntity( malProperty, propertyMd5Hash, MALPropertyStatus.OBSERVED );
                malPropertyRepository.save(malPropertyEntity);
            }
        });
    }
}
