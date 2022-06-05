package com.geektrust.backend.exceptions;

public class InputDataErrorException extends RuntimeException{
    
    public InputDataErrorException()
    {
        super();
    }

    public InputDataErrorException(String message)
    {
        super(message);
    }
}
