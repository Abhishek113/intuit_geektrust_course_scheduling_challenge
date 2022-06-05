package com.geektrust.backend.dtos;

import com.geektrust.backend.entities.CourseOffering;

public class AddCourseOfferingDto {

    private final CourseOffering courseOffering;

    public AddCourseOfferingDto(CourseOffering courseOffering)
    {
        this.courseOffering = courseOffering;
    }

    @Override
    public String toString() {
        
        return courseOffering.getId();
    }
    
}
