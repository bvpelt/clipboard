package bsoft.com.clipboard.application.controller;

public class BadParameterException extends RuntimeException {

    public BadParameterException() {

    }

    public BadParameterException(final String msg) {
        super(msg);
    }
}
