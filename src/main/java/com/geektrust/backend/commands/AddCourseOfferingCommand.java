package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.dtos.AddCourseOfferingDto;
import com.geektrust.backend.entities.AcknowledgementMessages;
import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.services.ICourseOfferingService;

public class AddCourseOfferingCommand implements ICommand{
    
    private static final Integer minNumberOfInputValues = 6;
    private final ICourseOfferingService courseOfferingService;

    public AddCourseOfferingCommand(ICourseOfferingService courseOfferingService)
    {
        this.courseOfferingService = courseOfferingService;
    }

    @Override
    public void execute(List<String> values) {
        
        try {
            if(values.size() < minNumberOfInputValues)
            {
                System.out.println(AcknowledgementMessages.INPUT_DATA_ERROR);
                return;
            }
            
            
            String courseName = values.get(1), author = values.get(2), date = values.get(3); 
            Integer minimumEmployees = Integer.parseInt(values.get(4)), maximumEmployees = Integer.parseInt(values.get(5));
            CourseOffering courseOffering = courseOfferingService.addCourseOffering(courseName, author, date, minimumEmployees, maximumEmployees);
            AddCourseOfferingDto addCourseOfferingDto = new AddCourseOfferingDto(courseOffering);
            System.out.println(addCourseOfferingDto);

        } catch (InputDataErrorException e) {
            System.out.println(e.getMessage());
        }

    }
}
