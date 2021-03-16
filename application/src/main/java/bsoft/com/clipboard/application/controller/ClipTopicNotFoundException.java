package bsoft.com.clipboard.application.controller;

public class ClipTopicNotFoundException extends RuntimeException {

    public ClipTopicNotFoundException() {

    }

    public ClipTopicNotFoundException(final String msg) {
        super(msg);
    }
}
