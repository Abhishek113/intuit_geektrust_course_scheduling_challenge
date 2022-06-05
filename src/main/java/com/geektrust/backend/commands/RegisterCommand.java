package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.dtos.RegisterDto;
import com.geektrust.backend.entities.AcknowledgementMessages;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.services.IRegistrationService;

public class RegisterCommand implements ICommand{
    
    private final IRegistrationService registrationService;
    private final Integer minInputValuesForRegistration = 3;
    
    public RegisterCommand(IRegistrationService registrationService)
    {
        this.registrationService = registrationService;
    }

    @Override
    public void execute(List<String> values) {
     
        try {
            if(values.size() < minInputValuesForRegistration)
                throw new InputDataErrorException(AcknowledgementMessages.INPUT_DATA_ERROR.getMessage());
            
            String emailId = values.get(1);
            String courseOfferingId = values.get(2);

            Registration registration = this.registrationService.registerToCourseOffering(emailId, courseOfferingId);
            RegisterDto registerDto = new RegisterDto(registration);

            System.out.println(registerDto);
            
        } catch (InputDataErrorException e) {
            System.out.println(e.getMessage());
        }
    }
}
