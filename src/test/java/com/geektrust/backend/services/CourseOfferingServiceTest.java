package com.geektrust.backend.services;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.ICourseOfferingRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RegistrationEntityTest")
@ExtendWith(MockitoExtension.class)
public class CourseOfferingServiceTest {

    // private String expectedOutput = "OFFERING-JAVA-JAMES";
    private CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
    private ICourseOfferingRepository courseOfferingRepository = new CourseOfferingRepository();
    private CourseOfferingService courseOfferingService = new CourseOfferingService(courseOfferingRepository);


    @Test
    @DisplayName("addCourseOffering should add course offering succesfuly")
    public void addCourseOfferingShouldAddCourseOfferingSuccessfuly()
    {
        CourseOffering actualCourseOffering = courseOfferingService.addCourseOffering("JAVA", "JAMES", "15062022", 1, 2);

        Assertions.assertEquals(courseOffering, actualCourseOffering);
    }

    @Test
    @DisplayName("addCourseOffering should throw InputDataErrorException exception on 0 minimum employees")
    public void addCourseOfferingShouldThrowInputDataErrorExceptionExceptionOnZeroMinimumEmployees()
    {
        Assertions.assertThrows(InputDataErrorException.class, ()->courseOfferingService.addCourseOffering("JAVA", "JAMES", "15062022", 0, 2));
    }

    @Test
    @DisplayName("addCourseOffering should throw InputDataErrorException exception on 0 maximum employees")
    public void addCourseOfferingShouldThrowInputDataErrorExceptionExceptionOnZeroMaximumEmployees()
    {
        Assertions.assertThrows(InputDataErrorException.class, ()->courseOfferingService.addCourseOffering("JAVA", "JAMES", "15062022", 1, 0));
    }

    @Test
    @DisplayName("addCourseOffering should throw InputDataErrorException exception if minimum employees are greater than maximum employees")
    public void addCourseOfferingShouldThrowInputDataErrorExceptionExceptionIfminimumEmployeesGreaterThanMaximumEmployees()
    {
        Assertions.assertThrows(InputDataErrorException.class, ()->courseOfferingService.addCourseOffering("JAVA", "JAMES", "15062022", 2, 1));
    }

    @Test
    @DisplayName("addCourseOffering should not add duplicate course offering")
    public void addCourseOfferingShouldNotAddDuplicateCourseOffering()
    {
        this.courseOfferingRepository.save(this.courseOffering);
        CourseOffering duplicateCourseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);

        duplicateCourseOffering = this.courseOfferingRepository.save(duplicateCourseOffering);

        Integer expectedNumberOfCourseOffering = 1;

        Assertions.assertEquals(expectedNumberOfCourseOffering, this.courseOfferingRepository.findAll().size());
    }

    
}
