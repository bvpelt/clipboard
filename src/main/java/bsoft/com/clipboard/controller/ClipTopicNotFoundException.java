package bsoft.com.clipboard.controller;

public class ClipTopicNotFoundException extends RuntimeException {

    public ClipTopicNotFoundException() {

    }

    public ClipTopicNotFoundException(final String msg) {
        super(msg);
    }
}
