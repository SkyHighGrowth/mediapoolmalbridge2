package com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents common fields in MediaWebServicePort method responses
 */
public abstract class AbstractBMResponse {

    /**
     * indicates success of the response
     */
    protected boolean status;

    /**
     * holds error list in the response
     */
    protected List<String> errors = new ArrayList<>();

    /**
     * holds warning list of the response
     */
    protected List<String> warnings = new ArrayList<>();

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getErrorAsString() {
        return String.join(",", errors);
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public String getWarningsAsString() {
        return String.join(",", warnings);
    }
}
