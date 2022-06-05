package com.geektrust.backend.entities;

public enum UserValidationValues {
    
    EMAIL_SUFFIX_UP("@GMAIL.COM"), EMAIL_SUFFIX_LOW("@gmail.com");

    private String emailSuffix;

    private UserValidationValues(String emailSuffix)
    {
        this.emailSuffix = emailSuffix;
    }

    public String getEmailSuffix()
    {
        return this.emailSuffix;
    }
}

