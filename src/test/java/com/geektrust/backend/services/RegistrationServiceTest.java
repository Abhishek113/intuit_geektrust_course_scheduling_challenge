package com.geektrust.backend.services;

import java.util.ArrayList;
import java.util.List;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.RegistrationRepository;
import com.geektrust.backend.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RegistrationEntityTest")
@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    private RegistrationRepository registrationRepository = new RegistrationRepository();
    private CourseOfferingRepository courseOfferingRepository = new CourseOfferingRepository();
    private UserRepository userRepository = new UserRepository();
    private RegistrationService registrationService = new RegistrationService(registrationRepository, courseOfferingRepository, userRepository);


    @Test
    @DisplayName("registerToCourseOffering should make resgistration successfull")
    public void registerToCourseOfferingShouldMakeRegistrationSuccessful()
    {
        try {

            User user = new User("ANDY@GMAIL.COM");
            userRepository.save(user);
            CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
            courseOfferingRepository.save(courseOffering);

            String expectedOutput = "Registration id: REG-COURSE-ANDY-JAVA User: ANDY course offering: JAVA";

            Registration registration = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

            Assertions.assertEquals(expectedOutput, registration.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("registerToCourseOffering Should use Already Created User")
    public void registerToCourseOfferingShouldUseAlreadyCreatedUser()
    {
        try {

            User user = new User("ANDY@GMAIL.COM");
            userRepository.save(user);
            CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
            courseOfferingRepository.save(courseOffering);

            // String expectedOutput = "Registration id: REG-COURSE-ANDY-JAVA User: ANDY course offering: JAVA";

            Registration registration = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

            Assertions.assertEquals(user, registration.getUser());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("registerToCourseOffering should create new user if not created and save it to userrEpository")
    public void registerToCourseOfferingShouldCreateNewUserIfNotCreatedAndSaveItToUserrEpository()
    {
        try {

            User user = new User("ANDY@GMAIL.COM");
            
            CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
            courseOfferingRepository.save(courseOffering);

            // String expectedOutput = "Registration id: REG-COURSE-ANDY-JAVA User: ANDY course offering: JAVA";

            Registration registration = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

            Assertions.assertEquals(user, registration.getUser());
            Assertions.assertEquals(user, userRepository.findById(user.getEmailId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("registerToCourseOffering should set status of registration to COURSE_FULL on full capacity and not save it")
    public void registerToCourseOfferingShouldSetStatusOFREgistrationToCOURSE_FULLOnFullCapacityAndNotSaveIt()
    {
        CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        courseOfferingRepository.save(courseOffering);

        Registration registration1 = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");
        Registration registration2 = registrationService.registerToCourseOffering("SAM@GMAIL.COM", "OFFERING-JAVA-JAMES");
        Registration registration3 = registrationService.registerToCourseOffering("TIM@GMAIL.COM", "OFFERING-JAVA-JAMES");

        Assertions.assertEquals(RegistrationStatus.COURSE_FULL, registration3.getStatus());
    }

    @Test
    @DisplayName("registerToCourseOffering should not add duplicate registrations")
    public void registerToCourseOfferingShouldNotAddDuplicateRegistrations()
    {
        try {
            CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
            courseOfferingRepository.save(courseOffering);

            Registration registration1 = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");
            Registration registration2 = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

            Integer expectedTotalRegistration = 1;

            Assertions.assertEquals(expectedTotalRegistration, courseOffering.getTotalRegistrations());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("Allot should return correcte output")
    public void allotMethodShouldReturnOutput()
    {
        try {
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
            
            Assertions.assertEquals(expectedOutput, this.registrationService.allot(courseOffering3.getId()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    @DisplayName("Allot should return empty output on wrong sourse offering id")
    public void allotMethodShouldReturnEmptyOutputOnWrongCourseOfferingId()
    {
        try {
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
            
            Assertions.assertEquals(expectedOutput, this.registrationService.allot("xyzAbc"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
}
