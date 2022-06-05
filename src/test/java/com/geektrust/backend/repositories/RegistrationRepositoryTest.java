package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.List;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.NoRegistrationFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RegistrationEntityTest")
@ExtendWith(MockitoExtension.class)
public class RegistrationRepositoryTest {
    

    private User user = new User("ANDY@GMAIL.COM");
    private CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
    private Registration registration = new Registration(user, courseOffering);
    private RegistrationRepository registrationRepository = new RegistrationRepository();

    @Test
    @DisplayName("Save method should save the registration object correctly")
    public void saveMethodShouldSaveTheRegistrationCorrectly()
    {
        try {

            this.registrationRepository.save(registration);
            Registration actualRegistration = this.registrationRepository.findById(registration.getId()).orElseThrow(()->new NoRegistrationFoundException("Registration with id: " + registration.getId() + " not found!"));

            Assertions.assertEquals(registration, actualRegistration);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("findAll should return all the registration")
    public void findAllShouldReturnAllRegistration()
    {
        try {
            
            List<Registration> registrations = new ArrayList<>();
            registrations.add(new Registration(new User("ANDY@GMAIL.COM"), this.courseOffering));
            registrations.add(new Registration(new User("SANDY@GMAIL.COM"), this.courseOffering));
            registrations.add(new Registration(new User("XYZ@GMAIL.COM"), this.courseOffering));

            for(Registration registration_ : registrations)
                this.registrationRepository.save(registration_);
            
            Assertions.assertEquals(registrations.size(), this.registrationRepository.findAll().size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
