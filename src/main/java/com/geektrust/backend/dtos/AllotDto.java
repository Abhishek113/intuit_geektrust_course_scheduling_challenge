package com.geektrust.backend.dtos;

import java.util.List;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;

public class AllotDto {

    private final List<Registration> registrations;
    
    public AllotDto(List<Registration> registrations)
    {
        this.registrations = registrations;
    }

    @Override
    public String toString() {
        String output = "";

        for(Registration registration : registrations)
        {
            CourseOffering courseOffering = registration.getCourseOffering();
            output += registration.getId() + " " + registration.getUser().getEmailId() + " " + courseOffering.getId() + " " + courseOffering.getCourseName() + " " + courseOffering.getAuthor() + " " + courseOffering.getDate() + " " + registration.getStatus().toString();
            output += "\n";
        }
        return output.trim();
    }
    
}
