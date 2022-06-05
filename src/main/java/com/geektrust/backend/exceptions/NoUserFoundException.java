package com.geektrust.backend.exceptions;

public class NoUserFoundException extends RuntimeException{

    public NoUserFoundException() {
    }

    public NoUserFoundException(String message) {
        super(message);
    }
    
}
