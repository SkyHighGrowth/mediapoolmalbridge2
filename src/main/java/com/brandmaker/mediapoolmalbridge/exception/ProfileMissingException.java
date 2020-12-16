package com.brandmaker.mediapoolmalbridge.exception;

/**
 * Profile missing runtime exception
 */
public class ProfileMissingException extends RuntimeException {

    /**
     * ProfileMissingException constructor
     *
     * @param message Exception message
     */
    public ProfileMissingException(String message) {
        super(message);
    }
}
