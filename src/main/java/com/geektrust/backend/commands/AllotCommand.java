package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.dtos.AllotDto;
import com.geektrust.backend.entities.AcknowledgementMessages;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.services.IRegistrationService;

public class AllotCommand implements ICommand{

    private final IRegistrationService registrationService;
    private static final Integer minNumberOfInputValues = 2;

    public AllotCommand(IRegistrationService registrationService)
    {
        this.registrationService = registrationService;
    }

    @Override
    public void execute(List<String> values) {
        

        try {
            if(values.size() < minNumberOfInputValues)
            {
                System.out.println(AcknowledgementMessages.INPUT_DATA_ERROR);
                return;
            }
            
            String courseOfferingId = values.get(1);

            List<Registration> registrations = this.registrationService.allot(courseOfferingId);
            AllotDto allotDto = new AllotDto(registrations);
            System.out.println(allotDto);

        } catch (InputDataErrorException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
