package com.monitoratec.tokenservice.vtswalletservice.exception;


public class GenericSecurityException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message - the detail message.
     */
    public GenericSecurityException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message   - the detail message.
     * @param throwable - the cause
     */
    public GenericSecurityException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
