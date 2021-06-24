package org.fornever.java.exceptions;


public class NotImplementException extends RuntimeException {

    public NotImplementException() {
    }

    public NotImplementException(String message) {
        super(message);
    }

    public NotImplementException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementException(Throwable cause) {
        super(cause);
    }

    public NotImplementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
