package MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import com.brandmaker.webservices.mediapool.DeleteMediaResult;

public class DeleteMediaResponse extends AbstractBMResponse {

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

    public DeleteMediaResult getDeleteMediaResult() {
        return deleteMediaResult;
    }

    public void setDeleteMediaResult(DeleteMediaResult deleteMediaResult) {
        this.deleteMediaResult = deleteMediaResult;
    }
}
