package MediaPoolMalBridge.clients.rest;

/**
 * Class which respresents model of error response
 */
public class ErrorResponse {

    private String result;

    private String message;

    public ErrorResponse() {

    }

    public ErrorResponse(final String result, final String message) {
        this.result = result;
        this.message = message;
    }

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
