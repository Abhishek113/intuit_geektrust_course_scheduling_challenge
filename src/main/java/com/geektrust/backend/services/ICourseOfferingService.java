package com.geektrust.backend.services;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.exceptions.InputDataErrorException;

public interface ICourseOfferingService {
    
    public CourseOffering addCourseOffering(String courseName, String author, String date, Integer minimumEmployees, 
                                            Integer maximumEmployees) throws InputDataErrorException;
}
