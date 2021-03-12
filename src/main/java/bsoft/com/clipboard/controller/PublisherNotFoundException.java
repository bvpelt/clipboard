package bsoft.com.clipboard.controller;

public class PublisherNotFoundException extends RuntimeException {

    public PublisherNotFoundException() {

    }

    public PublisherNotFoundException(final String msg) {
        super(msg);
    }
}
