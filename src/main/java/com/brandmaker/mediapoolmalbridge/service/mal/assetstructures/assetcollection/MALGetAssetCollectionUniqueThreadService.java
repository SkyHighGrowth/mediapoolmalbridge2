package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.assetcollection;

import com.brandmaker.mediapoolmalbridge.clients.mal.collections.client.MALGetCollectionsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.collections.client.model.Collection;
import com.brandmaker.mediapoolmalbridge.clients.mal.collections.client.model.MALGetCollectionsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import com.brandmaker.mediapoolmalbridge.model.mal.MALAssetStructures;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service that collects collections from MAL server
 */
@Service
public class MALGetAssetCollectionUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetCollectionsClient getCollectionsClient;

    private final MALAssetStructures assetStructures;

    public MALGetAssetCollectionUniqueThreadService(final MALGetCollectionsClient getCollectionsClient,
                                                    final MALAssetStructures assetStructures) {
        this.getCollectionsClient = getCollectionsClient;
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetCollectionsResponse> response = getCollectionsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getCollections() == null) {
            final String message = String.format("Invalid Collections response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        assetStructures.setCollections(response.getResponse()
                .getCollections()
                .stream()
                .collect(Collectors.toMap(Collection::getCollectionId, Collection::getName)));
    }
}
