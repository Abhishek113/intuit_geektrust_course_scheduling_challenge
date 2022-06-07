package com.geektrust.backend.entities;

public enum RegistrationStatus {
    
    ACCEPTED("ACCEPTED"), CONFIRMED("CONFIRMED"), COURSE_FULL("COURSE_FULL"), 
    CANCEL_ACCEPTED("CANCEL_ACCEPTED"), CANCEL_REJECTED("CANCEL_REJECTED");

    private String status;

    private RegistrationStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return this.status;
    }
}
