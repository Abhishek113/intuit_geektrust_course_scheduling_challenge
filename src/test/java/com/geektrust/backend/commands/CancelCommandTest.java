package com.geektrust.backend.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import com.geektrust.backend.dtos.CancelDto;
import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.User;
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
public class CancelCommandTest {
    
    private CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "05062022", 1, 2);
    private ICourseOfferingRepository courseOfferingRepository = new CourseOfferingRepository();
    private IRegistrationRepository registrationRepository = new RegistrationRepository();
    private IUserRepository userRepository = new UserRepository();
    private RegistrationService registrationService = new RegistrationService(registrationRepository, courseOfferingRepository, userRepository);
    private CancelCommand cancelCommand = new CancelCommand(registrationService);

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp()
    {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("cancelRegistration should accept cancellation")
    public void cancelRegistrationShouldAcceptCancellation()
    {
        // CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        this.courseOfferingRepository.save(courseOffering);

        User user1 = new User("ANDY@GMAIL.COM");
        this.userRepository.save(user1);

        Registration registration = this.registrationService.registerToCourseOffering(user1.getEmailId(), courseOffering.getId());
        
        List<String> values = List.of("CANCEL", "REG-COURSE-ANDY-JAVA");

        this.cancelCommand.execute(values);

        String expectedOuptut = "REG-COURSE-ANDY-JAVA CANCEL_ACCEPTED";

        Assertions.assertTrue(this.registrationRepository.findById(registration.getId()).isEmpty());

        Assertions.assertEquals(expectedOuptut, outputStreamCaptor.toString().trim());
    }    

    @Test
    @DisplayName("cancelRegistration should reject cancellation")
    public void cancelRegistrationShouldRejectCancellation()
    {
        // CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        this.courseOfferingRepository.save(courseOffering);

        User user1 = new User("ANDY@GMAIL.COM");
        this.userRepository.save(user1);


        Registration registration = this.registrationService.registerToCourseOffering(user1.getEmailId(), courseOffering.getId());
        
        List<Registration> allotedRegistrations = this.registrationService.allot(courseOffering.getId());
        
        List<String> values = List.of("CANCEL", "REG-COURSE-ANDY-JAVA");

        this.cancelCommand.execute(values);


        String expectedOuptut = "REG-COURSE-ANDY-JAVA CANCEL_REJECTED";

        Assertions.assertFalse(this.registrationRepository.findById(registration.getId()).isEmpty());

        Assertions.assertEquals(expectedOuptut, outputStreamCaptor.toString().trim());
    }  

    @AfterEach
    public void tearDown()
    {
        System.setOut(standardOut);
    }
}
