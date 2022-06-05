package com.geektrust.backend.services;

import java.util.List;
import java.util.Optional;

import com.geektrust.backend.entities.AcknowledgementMessages;
import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.CourseFullErrorException;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.exceptions.NoCourseOfferingFoundException;
import com.geektrust.backend.repositories.ICourseOfferingRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;
import com.geektrust.backend.repositories.IUserRepository;

public class RegistrationService implements IRegistrationService{
    
    private final IRegistrationRepository registrationRepository;
    private final ICourseOfferingRepository courseOfferingRepository;
    private final IUserRepository userRepository;

    public RegistrationService(IRegistrationRepository registrationRepository, ICourseOfferingRepository courseOfferingRepository, IUserRepository userRepository)
    {
        this.registrationRepository = registrationRepository;
        this.courseOfferingRepository = courseOfferingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Registration registerToCourseOffering(String emailId, String courseOfferingId) throws InputDataErrorException{


        CourseOffering courseOffering = courseOfferingRepository.findById(courseOfferingId).orElseThrow(()->new NoCourseOfferingFoundException("Course offering with id: " + courseOfferingId + " not found."));
        

        User user = null;
        Optional<User> checkUser = this.userRepository.findById(emailId);
        if(checkUser.isPresent())
        {
            user = checkUser.get();
        }
        else
        {
            user = new User(emailId);
        }
        
        Registration registration = new Registration(user, courseOffering);
        if(!this.idCourseOfferingCapacityFull(courseOffering))
        {
            registration.setStatus(RegistrationStatus.COURSE_FULL);
            return registration;
        }
        this.registrationRepository.save(registration);
        user.addCourseOffering(courseOffering);
        user = this.userRepository.save(user);
        
        courseOffering.incrementTotalRegistration();
        this.courseOfferingRepository.save(courseOffering);
        return registration;

    }

    private boolean idCourseOfferingCapacityFull(CourseOffering courseOffering)
    {
        if(courseOffering.getTotalRegistrations().equals(courseOffering.getMaximumEmployees()))
            return false;
        
        return true;
    }

    @Override
    public List<Registration> allot(String courseOfferingId) {
        
        return this.registrationRepository.allot(courseOfferingId);
    }
}