package com.geektrust.backend.entites;

import java.util.ArrayList;
import java.util.List;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RegistrationEntityTest")
@ExtendWith(MockitoExtension.class)
public class UserEntityTest {

    private User user = new User("ANDY@GMAIL.COM"); 

    @Test
    @DisplayName("User object should get created successfuly")
    public void userObjectShouldGetCreatedSuccessfuly()
    {
        try {
            String expectedOutput ="ANDY";
            Assertions.assertEquals(expectedOutput, user.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("User object should not get create on invalid email ID")
    public void userObjectShoudlNotGetCreatedOnInvalidEmailId()
    {
        try {
            User temp_user = new User("xyz");

            Assertions.assertNull(temp_user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("getUserCourseOfferings should return correct output")
    public void getUserCourseOfferingsShouldReturnCorrectOutput()
    {
        try {
            
            List<CourseOffering> courseOfferings = new ArrayList<>();
            courseOfferings.add(new CourseOffering("JAVA", "JAMES", "15062022", 1, 2));
            courseOfferings.add(new CourseOffering("Python", "JAMES", "15062022", 1, 2));
            courseOfferings.add(new CourseOffering("AWS", "JAMES", "15062022", 1, 2));
            
            for(CourseOffering courseOffering : courseOfferings)
                this.user.addCourseOffering(courseOffering);
            
            Assertions.assertEquals(courseOfferings, user.getUserCourseOfferings());
            

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
