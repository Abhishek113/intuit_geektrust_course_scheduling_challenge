package com.geektrust.backend.exceptions;

public class CourseFullErrorException extends RuntimeException{

    public CourseFullErrorException() {
    }

    public CourseFullErrorException(String message) {
        super(message);
    }
    
}
