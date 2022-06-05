package com.geektrust.backend.appConfig;

import com.geektrust.backend.commands.AddCourseOfferingCommand;
import com.geektrust.backend.commands.AllotCommand;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.commands.ICommand;
import com.geektrust.backend.commands.RegisterCommand;
import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.ICourseOfferingRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;
import com.geektrust.backend.repositories.IUserRepository;
import com.geektrust.backend.repositories.RegistrationRepository;
import com.geektrust.backend.repositories.UserRepository;
import com.geektrust.backend.services.CourseOfferingService;
import com.geektrust.backend.services.ICourseOfferingService;
import com.geektrust.backend.services.IRegistrationService;
import com.geektrust.backend.services.RegistrationService;

public class ApplicationConfiguration {

    ICourseOfferingRepository courseOfferingRepository = new CourseOfferingRepository();
    ICourseOfferingService courseOfferingService = new CourseOfferingService(courseOfferingRepository);

    IRegistrationRepository registrationRepository = new RegistrationRepository();
    IUserRepository userRepository = new UserRepository();
    IRegistrationService registrationService = new RegistrationService(registrationRepository, courseOfferingRepository, userRepository);


    ICommand addCourseOfferingCommand = new AddCourseOfferingCommand(courseOfferingService);
    ICommand registerCommand = new RegisterCommand(registrationService);
    ICommand allotCommand = new AllotCommand(registrationService);

    CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker()
    {
        commandInvoker.register("ADD-COURSE-OFFERING", addCourseOfferingCommand);
        commandInvoker.register("REGISTER", registerCommand);
        commandInvoker.register("ALLOT", allotCommand);

        return commandInvoker;
    }
    
}
