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
            output = registration.getId() + " " + RegistrationStatus.ACCEPTED;
        else
            output = RegistrationStatus.COURSE_FULL.toString();

        // output = registration.getId() + " " + registration.getStatus();
        return output.trim();
    }
    
}
