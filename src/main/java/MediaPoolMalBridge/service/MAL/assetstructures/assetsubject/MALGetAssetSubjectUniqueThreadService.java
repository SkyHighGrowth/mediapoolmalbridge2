package MediaPoolMalBridge.service.MAL.assetstructures.assetsubject;

import MediaPoolMalBridge.clients.MAL.subjects.client.MALGetSubjectsClient;
import MediaPoolMalBridge.clients.MAL.subjects.client.model.MALGetSubjectsResponse;
import MediaPoolMalBridge.clients.MAL.subjects.client.model.Subject;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
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
