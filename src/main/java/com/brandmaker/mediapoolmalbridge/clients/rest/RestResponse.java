package com.brandmaker.mediapoolmalbridge.clients.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * Class which models rest response
 * @param <RESPONSE> - response
 */
public class RestResponse<RESPONSE> {

    private HttpStatus httpStatus;

    private HttpHeaders httpHeaders;

    /**
     * {@link ErrorResponse}
     */
    private ErrorResponse errorResponse;

    private RESPONSE response;

    public RestResponse(final String error) {
        this.errorResponse = new ErrorResponse("failure", error);
    }

    private RestResponse(final HttpStatus httpStatus, final HttpHeaders httpHeaders) {
        this.httpStatus = httpStatus;
        this.httpHeaders = httpHeaders;
    }

    public RestResponse(final HttpStatus httpStatus, final HttpHeaders httpHeaders, final RESPONSE response) {
        this(httpStatus, httpHeaders);
        this.response = response;
        this.errorResponse = null;
    }

    public RestResponse(final HttpStatus httpStatus, final HttpHeaders httpHeaders, final ErrorResponse errorResponse) {
        this(httpStatus, httpHeaders);
        this.response = null;
        this.errorResponse = errorResponse;
    }

    public boolean isSuccess() {
        return HttpStatus.OK.equals(httpStatus) && errorResponse == null;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public RESPONSE getResponse() {
        return response;
    }

    public void setResponse(RESPONSE response) {
        this.response = response;
    }
}
