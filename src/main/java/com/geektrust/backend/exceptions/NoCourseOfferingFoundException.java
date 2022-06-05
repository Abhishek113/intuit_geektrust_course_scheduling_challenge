package com.geektrust.backend.exceptions;

public class NoCourseOfferingFoundException extends RuntimeException{

    public NoCourseOfferingFoundException() {
    }

    public NoCourseOfferingFoundException(String message) {
        super(message);
    }


    
}
