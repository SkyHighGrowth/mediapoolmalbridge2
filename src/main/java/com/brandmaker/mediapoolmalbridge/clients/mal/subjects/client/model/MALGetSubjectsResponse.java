package com.brandmaker.mediapoolmalbridge.clients.mal.subjects.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.subjects.client.MALGetSubjectsClient;

import java.util.List;

/**
 * Response of {@link MALGetSubjectsClient}
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
