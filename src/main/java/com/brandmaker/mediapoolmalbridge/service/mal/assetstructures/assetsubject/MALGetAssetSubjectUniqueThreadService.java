package com.brandmaker.mediapoolmalbridge.service.mal.assetstructures.assetsubject;

import com.brandmaker.mediapoolmalbridge.clients.mal.subjects.client.MALGetSubjectsClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.subjects.client.model.MALGetSubjectsResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.subjects.client.model.Subject;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import com.brandmaker.mediapoolmalbridge.model.mal.MALAssetStructures;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service that collects subjects from MAL server
 */
@Service
public class MALGetAssetSubjectUniqueThreadService extends AbstractMALUniqueThreadService {

    private final MALGetSubjectsClient getSubjectsClient;

    private final MALAssetStructures assetStructures;

    public MALGetAssetSubjectUniqueThreadService(final MALGetSubjectsClient getSubjectsClient,
                                                 final MALAssetStructures assetStructures) {
        this.getSubjectsClient = getSubjectsClient;
        this.assetStructures = assetStructures;
    }

    @Override
    protected void run() {
        final RestResponse<MALGetSubjectsResponse> response = getSubjectsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getSubjects() == null) {
            final String message = String.format("Invalid Subjects response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        assetStructures.setSubjects(response.getResponse()
                .getSubjects()
                .stream()
                .collect(Collectors.toMap(Subject::getSubjectId, Subject::getName)));
    }
}
