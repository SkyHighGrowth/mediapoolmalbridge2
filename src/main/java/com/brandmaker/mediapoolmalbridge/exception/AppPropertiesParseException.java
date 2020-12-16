package com.brandmaker.mediapoolmalbridge.exception;

/**
 * Application properties parse exception
 */
public class AppPropertiesParseException extends RuntimeException {

    /**
     * AppPropertiesParseException constructor
     *
     * @param message Exception message
     */
    public AppPropertiesParseException(String message) {
        super(message);
    }
}
