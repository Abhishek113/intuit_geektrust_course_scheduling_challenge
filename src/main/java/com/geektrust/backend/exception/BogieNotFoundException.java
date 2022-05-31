package com.geektrust.backend.exception;

public class BogieNotFoundException extends RuntimeException{

    public BogieNotFoundException()
    {
        super();
    }

    public BogieNotFoundException(String msg)
    {
        super(msg);
    }

    
}
