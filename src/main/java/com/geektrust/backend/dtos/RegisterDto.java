package com.geektrust.backend.dtos;

import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;

public class RegisterDto {

    private final Registration registration;

    public RegisterDto(Registration registration)
    {
        this.registration = registration;
    }

    @Override
    public String toString() {
        String output = "";
        if(!registration.getStatus().equals(RegistrationStatus.COURSE_FULL))
            output = registration.getId() + " " + RegistrationStatus.ACCEPTED.getStatus();
        else
            output = RegistrationStatus.COURSE_FULL.getStatus();

        // output = registration.getId() + " " + registration.getStatus();
        return output.trim();
    }
    
}
