package com.geektrust.backend.exception;

@SuppressWarnings("serial")
public class InvalidBogieException extends RuntimeException{
    
    public InvalidBogieException()
    {
        super();
    }

    public InvalidBogieException(String msg)
    {
        super(msg);
    }
}
