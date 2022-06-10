package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;

public class RegistrationRepository implements IRegistrationRepository{

    private final Map<String, Registration> registrationMap;

    public RegistrationRepository()
    {
        this.registrationMap = new HashMap<>();
    }
    

    public RegistrationRepository(Map<String, Registration> registrationMap) {
        this.registrationMap = registrationMap;
    }

    @Override
    public List<Registration> findAll() {
        if(this.registrationMap.size() == 0)
            return new ArrayList<>();
        
        return this.registrationMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public Optional<Registration> findById(String id) {
        return Optional.ofNullable(this.registrationMap.get(id));
    }

    @Override
    public Registration save(Registration registration) {

        this.registrationMap.put(registration.getId(), registration);

        return registration;
    }

    @Override
    public List<Registration> allot(String courseOfferingId) {
        List<Registration> registrations = this.findAll();
        registrations = registrations.stream().filter(registration -> registration.getCourseOffering().getId().equals(courseOfferingId)).collect(Collectors.toList());
        
        for(Registration currentRegistration: registrations)
        {
            if(currentRegistration.getCourseOffering().isMinimumCapacityFulfilled())
                currentRegistration.setStatus(RegistrationStatus.CONFIRMED);
            else
                currentRegistration.setStatus(RegistrationStatus.COURSE_CANCELED);
            this.save(currentRegistration);
        }
        Collections.sort(registrations, Registration.getSortByCourseRegistrationIdClass());

        return registrations;
    }

    @Override
    public Integer count() {
        return this.registrationMap.size();
    }

    @Override
    public void delete(Registration registration) {
        this.deleteById(registration.getId());
        
    }

    @Override
    public void deleteById(String id) {
        if(existsById(id))
            this.registrationMap.remove(id);
        
    }

    @Override
    public boolean existsById(String id) {
        if(this.registrationMap.containsKey(id))
            return true;
        return false;
    }

    @Override
    public void confirmRegistrationById(String registrationId)
    {
        if(existsById(registrationId))
        {
            Registration registration = this.registrationMap.get(registrationId);
            registration.setStatus(RegistrationStatus.CONFIRMED);
            registration = this.save(registration);
        }
    }

    // @Override
    // public void confirmAllRegistrations()
    // {
    //     for(Map.Entry<String, Registration> entry: registrationMap.entrySet())
    //     {
    //         Registration registration = entry.getValue();
    //         registration.setStatus(RegistrationStatus.CONFIRMED);
    //         registration = this.save(registration);
    //     }
    // }

    @Override
    public void confirmRegistrationsByCourseOffiering(CourseOffering courseOffering) {

        for(Map.Entry<String, Registration> entry: registrationMap.entrySet())
        {
            Registration registration = entry.getValue();
            if(registration.getCourseOffering().equals(courseOffering))
            {
                registration.setStatus(RegistrationStatus.CONFIRMED);
                registration = this.save(registration);
            }
        }
    }
    
}
