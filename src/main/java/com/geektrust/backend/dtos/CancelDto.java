package com.geektrust.backend.dtos;

import com.geektrust.backend.entities.RegistrationStatus;

public class CancelDto {

    private final String registrationId;
    private final RegistrationStatus registrationStatus;

    public CancelDto(String registrationId, RegistrationStatus registrationStatus )
    {
        this.registrationId = registrationId;
        this.registrationStatus = registrationStatus;
    }

    @Override
    public String toString() {
        return this.registrationId + " " + this.registrationStatus;
    }
    
}
