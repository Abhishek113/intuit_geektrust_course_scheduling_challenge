package com.geektrust.backend.appConfig;

import com.geektrust.backend.commands.AddCourseOfferingCommand;
import com.geektrust.backend.commands.AllotCommand;
import com.geektrust.backend.commands.CancelCommand;
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

    private final ICourseOfferingRepository courseOfferingRepository = new CourseOfferingRepository();
    private final ICourseOfferingService courseOfferingService = new CourseOfferingService(courseOfferingRepository);

    private final IRegistrationRepository registrationRepository = new RegistrationRepository();
    private final IUserRepository userRepository = new UserRepository();
    private final IRegistrationService registrationService = new RegistrationService(registrationRepository, courseOfferingRepository, userRepository);


    private final ICommand addCourseOfferingCommand = new AddCourseOfferingCommand(courseOfferingService);
    private final ICommand registerCommand = new RegisterCommand(registrationService);
    private final ICommand allotCommand = new AllotCommand(registrationService);
    private final ICommand cancelCommand = new CancelCommand(registrationService);

    CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker()
    {
        commandInvoker.register("ADD-COURSE-OFFERING", addCourseOfferingCommand);
        commandInvoker.register("REGISTER", registerCommand);
        commandInvoker.register("ALLOT", allotCommand);
        commandInvoker.register("CANCEL", cancelCommand);

        return commandInvoker;
    }
    
}
