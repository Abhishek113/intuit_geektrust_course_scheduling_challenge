package com.geektrust.backend.exception;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException()
    {
        super();
    }

    public InvalidInputException(String message)
    {
        super(message);
    }
    
}
