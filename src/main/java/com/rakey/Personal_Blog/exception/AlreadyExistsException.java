package com.rakey.Personal_Blog.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
