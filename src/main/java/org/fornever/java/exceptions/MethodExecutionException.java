package org.fornever.java.exceptions;

import java.lang.reflect.InvocationTargetException;

public class MethodExecutionException extends RuntimeException {

    public MethodExecutionException() {
    }

    public Throwable getInnerException() {
        return innerException;
    }

    private Throwable innerException;

    public MethodExecutionException(InvocationTargetException e) {
        super();
        this.innerException = e.getTargetException();
    }

    public MethodExecutionException(String message) {
        super(message);
    }

    public MethodExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodExecutionException(Throwable cause) {
        super(cause);
    }

    public MethodExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
