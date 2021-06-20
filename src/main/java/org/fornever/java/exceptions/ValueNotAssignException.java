package org.fornever.java.exceptions;

public class ValueNotAssignException extends RuntimeException {
    public ValueNotAssignException() {
    }

    public ValueNotAssignException(String message) {
        super(message);
    }

    public ValueNotAssignException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueNotAssignException(Throwable cause) {
        super(cause);
    }

    public ValueNotAssignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
