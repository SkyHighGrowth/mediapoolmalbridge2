package MediaPoolMalBridge.clients.MAL.subjects.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.subjects.client.MALGetSubjectsClient}
 */
public class MALGetSubjectsResponse extends MALAbstractResponse {

    /**
     * List of {@link Subject} objects
     */
    private List<Subject> subjects;

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
