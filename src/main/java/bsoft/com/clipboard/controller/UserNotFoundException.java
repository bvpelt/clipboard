package bsoft.com.clipboard.controller;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {

    }

    public UserNotFoundException(final String msg) {
        super(msg);
    }
}
