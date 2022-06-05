package com.geektrust.backend.services;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
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
    
}
