package com.geektrust.backend.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.ICourseOfferingRepository;
import com.geektrust.backend.services.CourseOfferingService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RegistrationEntityTest")
@ExtendWith(MockitoExtension.class)
public class AddCourseOfferingCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    // private CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
    private ICourseOfferingRepository courseOfferingRepository = new CourseOfferingRepository();
    private CourseOfferingService courseOfferingService = new CourseOfferingService(courseOfferingRepository);
    private AddCourseOfferingCommand addCourseOfferingCommand = new AddCourseOfferingCommand(courseOfferingService);

    @BeforeEach
    public void setUp()
    {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute should print course offering id")
    public void executeShouldPrintCourseOfferingId()
    {
        String expectedOutput = "OFFERING-JAVA-JAMES";
        addCourseOfferingCommand.execute(List.of("ADD-COURSE-OFFERING", "JAVA", "JAMES", "15062022", "1", "2"));

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("execute should print INPUT_DATA_ERROR given only course name")
    public void executeShouldPrintINPUT_DATA_ERRORMessageGivenOnlyCourseName()
    {
        String expectedOutput = "INPUT_DATA_ERROR";
        addCourseOfferingCommand.execute(List.of("ADD-COURSE-OFFERING", "JAVA"));

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }


    @AfterEach
    public void tearDown()
    {
        System.setOut(standardOut);
    }
    
}
