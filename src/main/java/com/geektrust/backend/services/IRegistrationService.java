package com.geektrust.backend.services;

import java.util.List;

import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.InputDataErrorException;

public interface IRegistrationService {
    
    public Registration registerToCourseOffering(String emailId, String courseOfferingId) throws InputDataErrorException;
    public List<Registration> allot(String courseOfferingId);
    public Registration cancelRegistration(String registrationId);
}
