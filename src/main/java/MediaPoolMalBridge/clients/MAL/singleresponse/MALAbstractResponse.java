package MediaPoolMalBridge.clients.MAL.singleresponse;

/**
 * Class that holds common fields of MAL server response
 */
public abstract class MALAbstractResponse {

    /**
     * result of the request, success if request is successful
     */
    private String result;

    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
