package com.brandmaker.mediapoolmalbridge.clients.mal.subjects.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of {@link MALGetSubjectsResponse}
 */
public class Subject {

    @SerializedName("subject_id")
    private String subjectId;

    private String name;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
