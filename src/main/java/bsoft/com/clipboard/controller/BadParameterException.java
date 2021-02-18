package bsoft.com.clipboard.controller;

public class BadParameterException extends RuntimeException {

    public BadParameterException() {

    }

    public BadParameterException(final String msg) {
        super(msg);
    }
}
