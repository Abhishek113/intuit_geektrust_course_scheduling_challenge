package com.geektrust.backend.services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.exceptions.NoCourseOfferingFoundException;
import com.geektrust.backend.exceptions.NoRegistrationFoundException;
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

        String registrationId = Registration.createId(user, courseOffering);
        Optional<Registration> registrationCheck = this.registrationRepository.findById(registrationId);
        if(registrationCheck.isPresent())
            return registrationCheck.get();
        
        Registration registration = null;
        if(this.isCourseOfferingCapacityFull(courseOffering))
        {
            registration = new Registration(user, courseOffering);
            registration.setStatus(RegistrationStatus.COURSE_FULL);
            return registration;
        }

        courseOffering.incrementTotalRegistration();
        courseOffering = this.courseOfferingRepository.save(courseOffering);

        user.addCourseOffering(courseOffering);
        user = this.userRepository.save(user);

        registration = new Registration(user, courseOffering);
        this.registrationRepository.save(registration);

        // if(courseOffering.getTotalRegistrations() == courseOffering.getMinimumEmployees())
        //     this.registrationRepository.confirmRegistrationsByCourseOffiering(courseOffering);
        
        // else if(courseOffering.getTotalRegistrations() > courseOffering.getMinimumEmployees())
        // this.registrationRepository.confirmRegistrationById(registration.getId());


        return registration;

    }

    private boolean isCourseOfferingCapacityFull(CourseOffering courseOffering)
    {
        if(courseOffering.getTotalRegistrations().equals(courseOffering.getMaximumEmployees()))
            return true;
        
        return false;
    }

    @Override
    public List<Registration> allot(String courseOfferingId) {

        Optional<CourseOffering> checkCourseOffering  = this.courseOfferingRepository.findById(courseOfferingId);
        
        if(checkCourseOffering.isPresent())
        {
            CourseOffering courseOffering = checkCourseOffering.get();
            if(courseOffering.getTotalRegistrations() < courseOffering.getMinimumEmployees())
                this.courseOfferingRepository.delete(courseOffering);
        }

        return this.registrationRepository.allot(courseOfferingId);
    }

    @Override
    public Registration cancelRegistration(String registrationId) {
        
        Registration registration = this.registrationRepository.findById(registrationId).orElseThrow(()->new NoRegistrationFoundException());
        if(registration.getStatus().equals(RegistrationStatus.CONFIRMED))
        {
            registration.setStatus(RegistrationStatus.CANCEL_REJECTED);
        }
        else
        {
            this.registrationRepository.delete(registration);
            registration.setStatus(RegistrationStatus.CANCEL_ACCEPTED);
        }

        return registration;
    }
}
