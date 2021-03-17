package bsoft.com.clipboard.storage.service;

public class PublisherNotFoundException extends RuntimeException {

    public PublisherNotFoundException() {

    }

    public PublisherNotFoundException(final String msg) {
        super(msg);
    }
}
