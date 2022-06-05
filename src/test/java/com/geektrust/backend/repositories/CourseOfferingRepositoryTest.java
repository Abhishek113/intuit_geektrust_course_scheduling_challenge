package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.exceptions.NoCourseOfferingFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RegistrationEntityTest")
@ExtendWith(MockitoExtension.class)
public class CourseOfferingRepositoryTest {
    

    @Test
    @DisplayName("Save method should save the course object correctly")
    public void saveMethodShouldSaveTheCourseCorrectly()
    {
        try {
            CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
            CourseOfferingRepository courseOfferingRepository = new CourseOfferingRepository();
            courseOffering = courseOfferingRepository.save(courseOffering);

            CourseOffering actualCourseOffering = courseOfferingRepository.findById(courseOffering.getId()).orElseThrow(()->new NoCourseOfferingFoundException());

            Assertions.assertEquals(courseOffering, actualCourseOffering);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
