package com.geektrust.backend.entites;

import com.geektrust.backend.entities.CourseOffering;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RegistrationEntityTest")
@ExtendWith(MockitoExtension.class)
public class CourseOfferingEntitytest {
    

    @Test
    @DisplayName("Course offering object should get successfuly created")
    public void courseOfferingObjectShouldGetSuccussefullyCreated()
    {
        String expectedOutput = "Course: JAVA id: OFFERING-JAVA-JAMES Author: JAMES Date: 15062022";
        CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);

        Assertions.assertEquals(expectedOutput, courseOffering.toString());
    }

    @Test
    @DisplayName("Creation of course offering object should create correct ID")
    public void creationOfCourseOfferingObjectShouldCreateCorrectId()
    {
        String expectedOutput = "OFFERING-JAVA-JAMES";
        CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);

        Assertions.assertEquals(expectedOutput, courseOffering.getId());
    }

}
