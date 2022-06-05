package com.geektrust.backend.entites;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;
import com.geektrust.backend.entities.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RegistrationEntityTest")
@ExtendWith(MockitoExtension.class)
public class RegistrationEntityTest {

    private User user = new User("ANDY@GMAIL.COM");
    private CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);

    private Registration registration = new Registration(user, courseOffering);

    @Test
    @DisplayName("Registration object should get created successfuly")
    public void registrationObjectShouldGetCreatedSuccessfuly()
    {
        try {
            String expectedOutput = "Registration id: REG-COURSE-ANDY-JAVA User: ANDY course offering: JAVA";
            Assertions.assertEquals(expectedOutput, registration.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Creation of registration object should create correct ID")
    public void creationOfRegistrationObjectShouldCreateCorrectId()
    {
        try {
            
            String expectedOutput = "REG-COURSE-ANDY-JAVA";

            Assertions.assertEquals(expectedOutput, registration.getId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("setStatus should set status correctly")
    public void setStatusShouldSetStatusCorrectly()
    {
        try{

            RegistrationStatus expectedOutput = RegistrationStatus.CONFIRMED;

            this.registration.setStatus(RegistrationStatus.CONFIRMED);

            Assertions.assertEquals(expectedOutput, this.registration.getStatus());

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }    

}
