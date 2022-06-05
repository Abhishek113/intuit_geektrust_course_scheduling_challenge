package com.geektrust.backend.services;

import com.geektrust.backend.entities.AcknowledgementMessages;
import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.repositories.ICourseOfferingRepository;

public class CourseOfferingService implements ICourseOfferingService{
    
    private final ICourseOfferingRepository courseOfferingRepository;

    public CourseOfferingService(ICourseOfferingRepository courseOfferingRepository)
    {
        this.courseOfferingRepository = courseOfferingRepository;
    }

    @Override
    public CourseOffering addCourseOffering(String courseName, String author, String date, Integer minimumEmployees,
            Integer maximumEmployees) throws InputDataErrorException{
        
        if(minimumEmployees.equals(0) || maximumEmployees.equals(0))
            throw new InputDataErrorException(AcknowledgementMessages.INPUT_DATA_ERROR.getMessage());
        
        if(minimumEmployees > maximumEmployees)
            throw new InputDataErrorException(AcknowledgementMessages.INPUT_DATA_ERROR.getMessage()); 
        
        if(courseName.equals("") || author.equals("") || date.equals(""))
            throw new InputDataErrorException(AcknowledgementMessages.INPUT_DATA_ERROR.getMessage());
        
        CourseOffering courseOffering = this.courseOfferingRepository.save(new CourseOffering(courseName, author, date, minimumEmployees, maximumEmployees));
        
        return courseOffering;

    }
}
