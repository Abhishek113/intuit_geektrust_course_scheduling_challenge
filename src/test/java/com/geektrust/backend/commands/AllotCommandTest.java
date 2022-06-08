package com.geektrust.backend.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.repositories.CourseOfferingRepository;
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
public class AllotCommandTest {
    
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final UserRepository userRepository = new UserRepository();
    private final CourseOfferingRepository courseOfferingRepository = new CourseOfferingRepository();
    private final RegistrationRepository registrationRepository = new RegistrationRepository();
    private final RegistrationService registrationService = new RegistrationService(registrationRepository, courseOfferingRepository, userRepository);
    private final ICommand allotCommand = new AllotCommand(registrationService);


    @BeforeEach
    public void setUp()
    {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Allot command should display result correctly")
    public void allotCommandSHouldDisplayResultCorrectly()
    {
        User user = new User("ANDY@GMAIL.COM");
        this.userRepository.save(user);
        CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "05062022", 1, 2);
        this.courseOfferingRepository.save(courseOffering);
        Registration registration = new Registration(user, courseOffering);
        this.registrationRepository.save(registration);

        List<String> values = List.of("ALLOT", "OFFERING-JAVA-JAMES");

        String expectedOutput = "REG-COURSE-ANDY-JAVA ANDY@GMAIL.COM OFFERING-JAVA-JAMES JAVA JAMES 05062022 CONFIRMED";

        this.allotCommand.execute(values);

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

    }

    @Test
    @DisplayName("Allot should also display the registration of cancelled course offering")
    public void allotShouldAlsoDisplayTheRegistrationOfCancelledCourseOffering()
    {
        User user = new User("ANDY@GMAIL.COM");
        this.userRepository.save(user);
        CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "05062022", 2, 2);
        this.courseOfferingRepository.save(courseOffering);
        Registration registration = this.registrationService.registerToCourseOffering("ANDY@GMAIL.COM", courseOffering.getId());
        this.registrationRepository.save(registration);

        List<String> values = List.of("ALLOT", "OFFERING-JAVA-JAMES");

        String expectedOutput = "REG-COURSE-ANDY-JAVA ANDY@GMAIL.COM OFFERING-JAVA-JAMES JAVA JAMES 05062022 CONFIRMED";

        this.allotCommand.execute(values);

        Assertions.assertTrue(this.courseOfferingRepository.findById(courseOffering.getId()).isEmpty());
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }


    @AfterEach
    public void tearDown()
    {
        System.setOut(standardOut);
    }
}
