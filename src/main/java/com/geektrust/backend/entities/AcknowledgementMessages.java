package com.geektrust.backend.entities;

public enum AcknowledgementMessages {

    CONFIRMED("CONFIRMED"), COURSE_FULL("COURSE_FULL"), 
    COURSE_CANCELED("COURSE_CANCELED"), CANCEL_ACCEPTED("CANCEL_ACCEPTED"), 
    CANCEL_REJECTED("CANCEL_REJECTED"), INPUT_DATA_ERROR("INPUT_DATA_ERROR");
    
    private String message;

    private AcknowledgementMessages(String messages)
    {
        this.message = messages;
    }

    public String getMessage()
    {
        return this.message;
    }

}
