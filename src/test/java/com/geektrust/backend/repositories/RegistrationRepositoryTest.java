package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.geektrust.backend.dtos.AllotDto;
import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;
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
        this.registrationRepository.save(registration);
        Registration actualRegistration = this.registrationRepository.findById(registration.getId()).orElseThrow(()->new NoRegistrationFoundException("Registration with id: " + registration.getId() + " not found!"));

        Assertions.assertEquals(registration, actualRegistration);
    }

    @Test
    @DisplayName("findAll should return all the registration")
    public void findAllShouldReturnAllRegistration()
    {
        List<Registration> registrations = new ArrayList<>();
        registrations.add(new Registration(new User("ANDY@GMAIL.COM"), this.courseOffering));
        registrations.add(new Registration(new User("SANDY@GMAIL.COM"), this.courseOffering));
        registrations.add(new Registration(new User("XYZ@GMAIL.COM"), this.courseOffering));

        for(Registration registration_ : registrations)
            this.registrationRepository.save(registration_);
        
        Assertions.assertEquals(registrations.size(), this.registrationRepository.findAll().size());

    }

    @Test
    @DisplayName("Allot should return correcte output")
    public void allotMethodShouldReturnOutput()
    {
        CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        CourseOffering courseOffering2 = new CourseOffering("Python", "Thomous", "15062022", 3, 5);
        CourseOffering courseOffering3 = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);
        
        User user1 = new User("ANDY@GMAIL.COM");
        User user2 = new User("SAM@GMAIL.COM");

        List<Registration> registrations = new ArrayList<>();
        registrations.add(new Registration(user1, courseOffering1));
        registrations.add(new Registration(user1, courseOffering3));
        registrations.add(new Registration(user2, courseOffering3));
        registrations.add(new Registration(user2, courseOffering2));

        List<Registration> expectedOutput = new ArrayList<>();
        expectedOutput.add(registrations.get(1));
        expectedOutput.add(registrations.get(2));            

        for(Registration registration_: registrations)
            this.registrationRepository.save(registration_);
        
        Assertions.assertEquals(expectedOutput, this.registrationRepository.allot(courseOffering3.getId()));

    }

    @Test
    @DisplayName("Allot should confirm respective registrations")
    public void allotMethodShouldConfirmRespectiveRegistrations()
    {
        CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        CourseOffering courseOffering2 = new CourseOffering("Python", "Thomous", "15062022", 3, 5);
        CourseOffering courseOffering3 = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);
        
        User user1 = new User("ANDY@GMAIL.COM");
        User user2 = new User("SAM@GMAIL.COM");

        List<Registration> registrations = new ArrayList<>();
        registrations.add(new Registration(user1, courseOffering1));
        registrations.add(new Registration(user1, courseOffering3));
        registrations.add(new Registration(user2, courseOffering3));
        registrations.add(new Registration(user2, courseOffering2));

        List<Registration> expectedOutput = new ArrayList<>();
        expectedOutput.add(registrations.get(1));
        expectedOutput.add(registrations.get(2));            

        for(Registration registration_: registrations)
            this.registrationRepository.save(registration_);
        
        List<Registration> actualOutput = this.registrationRepository.allot(courseOffering3.getId());
        Assertions.assertEquals(expectedOutput, actualOutput);

        for(Registration currentRegistration: actualOutput)
            Assertions.assertEquals(RegistrationStatus.CONFIRMED, currentRegistration.getStatus());

    }


    @Test
    @DisplayName("Allot should return empty output on wrong sourse offering id")
    public void allotMethodShouldReturnEmptyOutputOnWrongCourseOfferingId()
    {
        CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        CourseOffering courseOffering2 = new CourseOffering("Python", "Thomous", "15062022", 3, 5);
        CourseOffering courseOffering3 = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);
        
        User user1 = new User("ANDY@GMAIL.COM");
        User user2 = new User("SAM@GMAIL.COM");

        List<Registration> registrations = new ArrayList<>();
        registrations.add(new Registration(user1, courseOffering1));
        registrations.add(new Registration(user1, courseOffering3));
        registrations.add(new Registration(user2, courseOffering3));
        registrations.add(new Registration(user2, courseOffering2));

        List<Registration> expectedOutput = new ArrayList<>();          

        for(Registration registration_: registrations)
            this.registrationRepository.save(registration_);
        
        Assertions.assertEquals(expectedOutput, this.registrationRepository.allot("xyzAbc"));

    }

    @Test
    @DisplayName("count function should return correct number of registrations")
    public void countShouldReturnCorrectNumberOfRegistrations()
    {
       try {
           CourseOffering courseOffering = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);       
           User user = new User("ANDY@GMAIL.COM");

           Registration registration = new Registration(user, courseOffering);
           this.registrationRepository.save(registration);

           Integer expectedRegistration = 1;
           Assertions.assertEquals(expectedRegistration, this.registrationRepository.count());
       } catch (Exception e) {
            System.out.println(e.getMessage());
       }
    }

    @Test
    @DisplayName("existsById should return true or false correctly")
    public void existsByIdShouldReturnTrueOrFalseCorrectly()
    {
        try {
            CourseOffering courseOffering = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);       
            User user = new User("ANDY@GMAIL.COM");
 
            Registration registration = new Registration(user, courseOffering);
            this.registrationRepository.save(registration);
            
            Assertions.assertTrue(this.registrationRepository.existsById(registration.getId()));

            this.registrationRepository.delete(registration);

            Assertions.assertFalse(this.registrationRepository.existsById(registration.getId()));
            
        } catch (Exception e) {
             System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("delete function should successfuly delete registration entity")
    public void deleteShouldDeleteRegistrationEntitySuccessfuly()
    {
       try {
           CourseOffering courseOffering = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);       
           User user = new User("ANDY@GMAIL.COM");

           Registration registration = new Registration(user, courseOffering);
           this.registrationRepository.save(registration);
           this.registrationRepository.delete(registration);

           Integer expectedRegistration = 0;
           Assertions.assertEquals(expectedRegistration, this.registrationRepository.count());
       } catch (Exception e) {
            System.out.println(e.getMessage());
       }
    }

    @Test
    @DisplayName("deleteById should delete registration successfully")
    public void deleteByIdShouldDeleteRegistrationSuccessfully()
    {
       try {
           CourseOffering courseOffering = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);       
           User user = new User("ANDY@GMAIL.COM");

           Registration registration = new Registration(user, courseOffering);
           this.registrationRepository.save(registration);
           this.registrationRepository.deleteById(registration.getId());

           Integer expectedRegistration = 0;
           Assertions.assertEquals(expectedRegistration, this.registrationRepository.count());
       } catch (Exception e) {
            System.out.println(e.getMessage());
       }
    }

    @Test
    @DisplayName("confirmRegistrationsByCourseOffiering should make respective courseOffering registrations confirm")
    public void confirmRegistrationsByCourseOffieringShouldMakeRespectivecourseOfferingRegistrationsConfirm()
    {
        try {

            this.registrationRepository.save(this.registration);
            User user2 = new User("WOO@GMAIL.COM");
            Registration registration = new Registration(user2, this.courseOffering);
            registration = this.registrationRepository.save(registration);

            String expectedOutput = "REG-COURSE-ANDY-JAVA ANDY@GMAIL.COM OFFERING-JAVA-JAMES JAVA JAMES 15062022 CONFIRMED\nREG-COURSE-WOO-JAVA WOO@GMAIL.COM OFFERING-JAVA-JAMES JAVA JAMES 15062022 CONFIRMED";
            
            this.registrationRepository.confirmRegistrationsByCourseOffiering(this.courseOffering);
            List<Registration> confirmedRegistrations = this.registrationRepository.findAll();
            Collections.sort(confirmedRegistrations, Registration.getSortByCourseRegistrationIdClass());
            AllotDto allotDto = new AllotDto(confirmedRegistrations);

            Assertions.assertEquals(expectedOutput, allotDto.toString());
            Assertions.assertEquals(RegistrationStatus.CONFIRMED, registration.getStatus());
            Assertions.assertEquals(RegistrationStatus.CONFIRMED, this.registration.getStatus());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("confirmRegistrationById should make the respective registration confirm")
    public void confirmRegistrationByIdShouldMakeTheRespectiveRegistrationConfirm()
    {
        try {

            User user2 = new User("WOO@GMAIL.COM");
            Registration registration = new Registration(user2, this.courseOffering);
            registration = this.registrationRepository.save(registration);

            this.registrationRepository.confirmRegistrationById(registration.getId());

            Registration checkRegistration = this.registrationRepository.findById(registration.getId()).orElseThrow(()->new NoRegistrationFoundException());

            Assertions.assertEquals(RegistrationStatus.CONFIRMED, checkRegistration.getStatus());
            Assertions.assertEquals(RegistrationStatus.CONFIRMED, registration.getStatus());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
