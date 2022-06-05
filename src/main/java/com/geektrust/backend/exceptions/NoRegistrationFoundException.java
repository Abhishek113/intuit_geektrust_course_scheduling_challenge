package com.geektrust.backend.exceptions;

public class NoRegistrationFoundException extends RuntimeException{

    public NoRegistrationFoundException() {
    }

    public NoRegistrationFoundException(String message) {
        super(message);
    }
    
}
