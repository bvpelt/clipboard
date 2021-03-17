package bsoft.com.clipboard.storage.service;

public class BadParameterException extends RuntimeException {

    public BadParameterException() {

    }

    public BadParameterException(final String msg) {
        super(msg);
    }
}
