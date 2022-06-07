package com.geektrust.backend.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.ICourseOfferingRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;
import com.geektrust.backend.repositories.IUserRepository;
import com.geektrust.backend.repositories.RegistrationRepository;
import com.geektrust.backend.repositories.UserRepository;
import com.geektrust.backend.services.RegistrationService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RegistrationEntityTest")
@ExtendWith(MockitoExtension.class)
public class RegisterCommandTest {
    
    private CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "05062022", 1, 2);
    private ICourseOfferingRepository courseOfferingRepository = new CourseOfferingRepository();
    private IRegistrationRepository registrationRepository = new RegistrationRepository();
    private IUserRepository userRepository = new UserRepository();
    private RegistrationService registrationService = new RegistrationService(registrationRepository, courseOfferingRepository, userRepository);
    private RegisterCommand registerCommand = new RegisterCommand(registrationService);

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @BeforeEach
    public void setUp()
    {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("RegisterCommand ould print registration id and accepted status")
    public void RegisterCommandShouldPrintRegistrationIdAndACCEPTEDstatus()
    {
        this.courseOfferingRepository.save(this.courseOffering);

        List<String> values = List.of("REGISTER", "ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

        this.registerCommand.execute(values);

        String expectedOutput = "REG-COURSE-ANDY-JAVA ACCEPTED";

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

    }

    @Test
    @DisplayName("RegisterCommand should print COURSE_FULL status")
    public void RegisterCommandShouldPrintCOURSE_FULLstatus()
    {
        this.courseOfferingRepository.save(this.courseOffering);

        List<String> values = List.of("REGISTER", "ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

        this.registerCommand.execute(values);

        values = List.of("REGISTER", "WOO@GMAIL.COM", "OFFERING-JAVA-JAMES");

        this.registerCommand.execute(values);

        values = List.of("REGISTER", "ALICE@GMAIL.COM", "OFFERING-JAVA-JAMES");

        this.registerCommand.execute(values);

        String expectedOutput = "REG-COURSE-ANDY-JAVA ACCEPTED\nREG-COURSE-WOO-JAVA ACCEPTED\nCOURSE_FULL";

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

    }

    @Test
    @DisplayName("RegisterCommand should print INPUT_DATA_ERROR on wrong input")
    public void registerCommandShoulfPrintINPUT_DATA_ERROROnWrongInput()
    {
        this.courseOfferingRepository.save(this.courseOffering);

        List<String> values = List.of("REGISTER", "ANDY@GMAIL.COM");

        this.registerCommand.execute(values);

        Assertions.assertEquals("INPUT_DATA_ERROR", outputStreamCaptor.toString().trim());
    }


    @AfterEach
    public void tearDown()
    {
        System.setOut(standardOut);
    }
}   
