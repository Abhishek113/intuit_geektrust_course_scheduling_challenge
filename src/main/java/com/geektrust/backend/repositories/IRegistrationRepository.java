package com.geektrust.backend.repositories;

import java.util.List;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;

public interface IRegistrationRepository extends CRUDRepository<Registration, String>{
    
    public List<Registration> allot(String courseOfferingId);
    public void confirmRegistrationById(String registrationId);
    public void confirmRegistrationsByCourseOffiering(CourseOffering courseOffering);
}
