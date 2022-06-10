package com.geektrust.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.geektrust.backend.dtos.CancelDto;
import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;
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
        User user = new User("ANDY@GMAIL.COM");
        this.userRepository.save(user);
        CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        this.courseOfferingRepository.save(courseOffering);

        String expectedOutput = "Registration id: REG-COURSE-ANDY-JAVA User: ANDY course offering: JAVA";

        Registration registration = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

        Assertions.assertEquals(expectedOutput, registration.toString());
        Assertions.assertEquals(RegistrationStatus.ACCEPTED, registration.getStatus());
    }

    @Test
    @DisplayName("registerToCourseOffering Should use Already Created User")
    public void registerToCourseOfferingShouldUseAlreadyCreatedUser()
    {
        User user = new User("ANDY@GMAIL.COM");
        this.userRepository.save(user);
        CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        this.courseOfferingRepository.save(courseOffering);

        // String expectedOutput = "Registration id: REG-COURSE-ANDY-JAVA User: ANDY course offering: JAVA";

        Registration registration = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

        Assertions.assertEquals(user, registration.getUser());
    }

    @Test
    @DisplayName("registerToCourseOffering should create new user if not created and save it to userrEpository")
    public void registerToCourseOfferingShouldCreateNewUserIfNotCreatedAndSaveItToUserrEpository()
    {
        User user = new User("ANDY@GMAIL.COM");
            
        CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        this.courseOfferingRepository.save(courseOffering);

        // String expectedOutput = "Registration id: REG-COURSE-ANDY-JAVA User: ANDY course offering: JAVA";

        Registration registration = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

        Assertions.assertEquals(user, registration.getUser());
        Assertions.assertEquals(user, userRepository.findById(user.getEmailId()).get());
    }

    @Test
    @DisplayName("registerToCourseOffering should set status of registration to COURSE_FULL on full capacity and not save it")
    public void registerToCourseOfferingShouldSetStatusOFREgistrationToCOURSE_FULLOnFullCapacityAndNotSaveIt()
    {
        CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        this.courseOfferingRepository.save(courseOffering);

        Registration registration1 = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");
        Registration registration2 = registrationService.registerToCourseOffering("SAM@GMAIL.COM", "OFFERING-JAVA-JAMES");
        Registration registration3 = registrationService.registerToCourseOffering("TIM@GMAIL.COM", "OFFERING-JAVA-JAMES");

        Assertions.assertEquals(RegistrationStatus.COURSE_FULL, registration3.getStatus());
        Optional<Registration> checkSavedRegistration = this.registrationRepository.findById(registration3.getId());
        Assertions.assertTrue(checkSavedRegistration.isEmpty());
    }

    @Test
    @DisplayName("registerToCourseOffering should not add duplicate registrations")
    public void registerToCourseOfferingShouldNotAddDuplicateRegistrations()
    {
        CourseOffering courseOffering = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        this.courseOfferingRepository.save(courseOffering);

        Registration registration1 = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");
        Registration registration2 = registrationService.registerToCourseOffering("ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

        Integer expectedTotalRegistration = 1;

        Assertions.assertEquals(expectedTotalRegistration, courseOffering.getTotalRegistrations());

    }

    // @Test
    // @DisplayName("registerToCourseOffering should confirm registartions after minimum number of employees register")
    // public void registerToCourseOfferingShouldConfirmRegistrationAfterMinimumNumberOfEmployeesregister()
    // {
    //     CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 2, 3);

    //     Registration registration1 = this.registrationService.registerToCourseOffering("ANDY@GMAIL.COM", courseOffering1.getId());
    //     Assertions.assertEquals(RegistrationStatus.ACCEPTED, registration1.getStatus());
    //     Registration registration2 = this.registrationService.registerToCourseOffering("SAM@GMAIL.COM", courseOffering1.getId());
    //     Assertions.assertEquals(RegistrationStatus.CONFIRMED, registration1.getStatus());
    //     Assertions.assertEquals(RegistrationStatus.CONFIRMED, registration2.getStatus());

    //     Registration registration3 = this.registrationService.registerToCourseOffering("TOM@GMAIL.COM", courseOffering1.getId());
    //     Assertions.assertEquals(RegistrationStatus.CONFIRMED, registration3.getStatus());

    // }

    @Test
    @DisplayName("Allot should return correcte output")
    public void allotMethodShouldReturnCorrectOutput()
    {
        CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        CourseOffering courseOffering2 = new CourseOffering("Python", "Thomous", "15062022", 3, 5);
        CourseOffering courseOffering3 = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);
        
        this.courseOfferingRepository.save(courseOffering1);
        this.courseOfferingRepository.save(courseOffering2);
        this.courseOfferingRepository.save(courseOffering3);

        // User user1 = new User("ANDY@GMAIL.COM");
        // User user2 = new User("SAM@GMAIL.COM");

        List<Registration> registrations = new ArrayList<>();
        registrations.add(this.registrationService.registerToCourseOffering("ANDY@GMAIL.COM", courseOffering1.getId()));
        registrations.add(this.registrationService.registerToCourseOffering("ANDY@GMAIL.COM", courseOffering3.getId()));
        registrations.add(this.registrationService.registerToCourseOffering("SAM@GMAIL.COM", courseOffering3.getId()));
        registrations.add(this.registrationService.registerToCourseOffering("SAM@GMAIL.COM", courseOffering2.getId()));

        List<Registration> expectedOutput = new ArrayList<>();
        expectedOutput.add(registrations.get(1));
        expectedOutput.add(registrations.get(2));            

        // for(Registration registration_: registrations)
        //     this.registrationRepository.save(registration_);
        
        Assertions.assertEquals(expectedOutput, this.registrationService.allot(courseOffering3.getId()));

    }


    @Test
    @DisplayName("Allot should delete course offering if minimum enrollment not done")
    public void allotMethodShouldDeleteCourseOfferingIfMinimumEnrollmentNotDone()
    {
        CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        CourseOffering courseOffering2 = new CourseOffering("Python", "Thomous", "15062022", 3, 5);
        CourseOffering courseOffering3 = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);
        
        this.courseOfferingRepository.save(courseOffering1);
        this.courseOfferingRepository.save(courseOffering2);
        this.courseOfferingRepository.save(courseOffering3);

        // User user1 = new User("ANDY@GMAIL.COM");
        // User user2 = new User("SAM@GMAIL.COM");

        List<Registration> registrations = new ArrayList<>();
        registrations.add(this.registrationService.registerToCourseOffering("ANDY@GMAIL.COM", courseOffering1.getId()));
        registrations.add(this.registrationService.registerToCourseOffering("ANDY@GMAIL.COM", courseOffering3.getId()));
        // registrations.add(this.registrationService.registerToCourseOffering("SAM@GMAIL.COM", courseOffering3.getId()));
        registrations.add(this.registrationService.registerToCourseOffering("SAM@GMAIL.COM", courseOffering2.getId()));

        List<Registration> expectedOutput = new ArrayList<>();
        expectedOutput.add(registrations.get(1));
        expectedOutput.add(registrations.get(2));            

        // for(Registration registration_: registrations)
        //     this.registrationRepository.save(registration_);
        
        //Assertions.assertEquals(expectedOutput, this.registrationService.allot(courseOffering3.getId()));
        List<Registration> actualRegistrations = this.registrationService.allot(courseOffering3.getId());

        Assertions.assertTrue(this.courseOfferingRepository.findById(courseOffering3.getId()).isEmpty());

    }

    @Test
    @DisplayName("Allot should display list even after course offeirng get deleted")
    public void allotShouldDisplayListEvenAfterCourseOfferingGetDeleted()
    {
        CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 3, 3);
        this.courseOfferingRepository.save(courseOffering1);
        // User user1 = new User("ANDY@GMAIL.COM");
        // User user2 = new User("SAM@GMAIL.COM");

        List<Registration> registrations = new ArrayList<>();
        registrations.add(this.registrationService.registerToCourseOffering("ANDY@GMAIL.COM", courseOffering1.getId()));
        registrations.add(this.registrationService.registerToCourseOffering("SAM@GMAIL.COM", courseOffering1.getId()));
        // List<Registration> expectedOutput = new ArrayList<>();
        // expectedOutput.add(registrations.get(0));
        // expectedOutput.add(registrations.get(1));
        
        // for(Registration registration_: registrations)
        //     this.registrationRepository.save(registration_);
        
        //Assertions.assertEquals(expectedOutput, this.registrationService.allot(courseOffering3.getId()));
        List<Registration> actualRegistrations = this.registrationService.allot(courseOffering1.getId());
        Assertions.assertTrue(this.courseOfferingRepository.findById(courseOffering1.getId()).isEmpty());
        Assertions.assertEquals(registrations, actualRegistrations);

    }

    @Test
    @DisplayName("Allot should return empty output on wrong sourse offering id")
    public void allotMethodShouldReturnEmptyOutputOnWrongCourseOfferingId()
    {
        
        CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        CourseOffering courseOffering2 = new CourseOffering("Python", "Thomous", "15062022", 3, 5);
        CourseOffering courseOffering3 = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);
        
        this.courseOfferingRepository.save(courseOffering1);
        this.courseOfferingRepository.save(courseOffering2);
        this.courseOfferingRepository.save(courseOffering3);


        // User user1 = new User("ANDY@GMAIL.COM");
        // User user2 = new User("SAM@GMAIL.COM");

        List<Registration> registrations = new ArrayList<>();
        registrations.add(this.registrationService.registerToCourseOffering("ANDY@GMAIL.COM", courseOffering1.getId()));
        registrations.add(this.registrationService.registerToCourseOffering("ANDY@GMAIL.COM", courseOffering3.getId()));
        registrations.add(this.registrationService.registerToCourseOffering("SAM@GMAIL.COM", courseOffering3.getId()));
        registrations.add(this.registrationService.registerToCourseOffering("SAM@GMAIL.COM", courseOffering2.getId()));


        List<Registration> expectedOutput = new ArrayList<>();          

        // for(Registration registration_: registrations)
           // this.registrationRepository.save(registration_);
        
        Assertions.assertEquals(expectedOutput, this.registrationService.allot("xyzAbc"));

    }

    @Test
    @DisplayName("cancelRegistration should accept cancellation")
    public void cancelRegistrationShouldAcceptCancellation()
    {
        CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        this.courseOfferingRepository.save(courseOffering1);

        User user1 = new User("ANDY@GMAIL.COM");
        this.userRepository.save(user1);

        Registration registration = this.registrationService.registerToCourseOffering(user1.getEmailId(), courseOffering1.getId());
        CancelDto cancelDto = this.registrationService.cancelRegistration(registration.getId());

        String expectedOuptut = "REG-COURSE-ANDY-JAVA CANCEL_ACCEPTED";

        Assertions.assertTrue(this.registrationRepository.findById(registration.getId()).isEmpty());

        Assertions.assertEquals(expectedOuptut, cancelDto.toString());
    }    

    @Test
    @DisplayName("cancelRegistration should reject cancellation")
    public void cancelRegistrationShouldRejectCancellation()
    {
        CourseOffering courseOffering1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        this.courseOfferingRepository.save(courseOffering1);

        User user1 = new User("ANDY@GMAIL.COM");
        this.userRepository.save(user1);


        Registration registration = this.registrationService.registerToCourseOffering(user1.getEmailId(), courseOffering1.getId());
        
        List<Registration> allotedRegistrations = this.registrationService.allot(courseOffering1.getId());
        
        CancelDto cancelDto = this.registrationService.cancelRegistration(registration.getId());

        

        String expectedOuptut = "REG-COURSE-ANDY-JAVA CANCEL_REJECTED";

        Assertions.assertFalse(this.registrationRepository.findById(registration.getId()).isEmpty());

        Assertions.assertEquals(expectedOuptut, cancelDto.toString());
    }  
}
