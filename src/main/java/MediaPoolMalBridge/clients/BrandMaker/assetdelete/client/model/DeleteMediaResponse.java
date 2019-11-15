package MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model;

import com.brandmaker.webservices.mediapool.DeleteMediaResult;

import java.util.ArrayList;
import java.util.List;

public class DeleteMediaResponse {

    private boolean status;

    private List<String> errors = new ArrayList<>();

    private List<String> warnings = new ArrayList<>();

    private DeleteMediaResult deleteMediaResult;

    public DeleteMediaResponse(final DeleteMediaResult deleteMediaResult) {
        this.status = deleteMediaResult.isSuccess();
        if (!deleteMediaResult.isSuccess()) {
            deleteMediaResult.getErrors().forEach(x -> errors.add(x.getError()));
            deleteMediaResult.getWarnings().forEach(x -> warnings.add(x.getWarning()));
        }
        this.deleteMediaResult = deleteMediaResult;
    }

    public DeleteMediaResponse(final boolean status, final String error) {
        this.status = status;
        errors.add(error);
    }

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

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public DeleteMediaResult getDeleteMediaResult() {
        return deleteMediaResult;
    }

    public void setDeleteMediaResult(DeleteMediaResult deleteMediaResult) {
        this.deleteMediaResult = deleteMediaResult;
    }
}
