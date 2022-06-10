package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.dtos.CancelDto;
import com.geektrust.backend.entities.AcknowledgementMessages;
import com.geektrust.backend.exceptions.NoRegistrationFoundException;
import com.geektrust.backend.services.IRegistrationService;

public class CancelCommand implements ICommand{
    
    private final IRegistrationService registrationService;
    private final Integer minInputValuesForRegistration = 2;

    public CancelCommand(IRegistrationService registrationService)
    {
        this.registrationService = registrationService;
    }

    @Override
    public void execute(List<String> values) {
        try {
            
            if(values.size() < minInputValuesForRegistration)
            {
                System.out.println(AcknowledgementMessages.INPUT_DATA_ERROR);
                return;
            }

            String registrationId = values.get(1);
            CancelDto cancelDto = this.registrationService.cancelRegistration(registrationId);
            System.out.println(cancelDto);

        } catch (NoRegistrationFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
