package MediaPoolMalBridge.service.MAL.assetstructures.assetsubject;

import MediaPoolMalBridge.clients.MAL.subjects.client.MALGetSubjectsClient;
import MediaPoolMalBridge.clients.MAL.subjects.client.model.MALGetSubjectsResponse;
import MediaPoolMalBridge.clients.MAL.subjects.client.model.Subject;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetStructures;
import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MALGetAssetSubjectService extends AbstractMALService {

    private final MALGetSubjectsClient getSubjectsClient;

    private final MALAssetStructures malAssetStructures;

    public MALGetAssetSubjectService(final MALGetSubjectsClient getSubjectsClient,
                                     final MALAssetStructures malAssetStructures) {
        this.getSubjectsClient = getSubjectsClient;
        this.malAssetStructures = malAssetStructures;
    }

    public void download() {
        final RestResponse<MALGetSubjectsResponse> response = getSubjectsClient.download();
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                response.getResponse().getSubjects() == null) {
            final String message = String.format("Invalid Subjects response [%s]", GSON.toJson(response));
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message);
            return;
        }

        malAssetStructures.setSubjects(response.getResponse()
                .getSubjects()
                .stream()
                .collect(Collectors.toMap(Subject::getSubjectId, Subject::getName)));
    }
}
