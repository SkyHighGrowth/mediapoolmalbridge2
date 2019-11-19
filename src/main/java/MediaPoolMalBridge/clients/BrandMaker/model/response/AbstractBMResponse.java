package MediaPoolMalBridge.clients.BrandMaker.model.response;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBMResponse {

    protected boolean status;

    protected List<String> errors = new ArrayList<>();

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
