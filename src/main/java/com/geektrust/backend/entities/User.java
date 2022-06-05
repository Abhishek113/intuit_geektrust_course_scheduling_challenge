package com.geektrust.backend.entities;

import java.util.ArrayList;
import java.util.List;

import com.geektrust.backend.exceptions.InputDataErrorException;

public class User extends BaseEnitiy{

    private String emailId; 

    private List<CourseOffering> userCourseOfferings;
    
    public User(String emailId)
    {
        validateUserEmail(emailId);
        this.emailId = emailId;
        userCourseOfferings = new ArrayList<>();
    }

    public User(String id, String emailId, List<CourseOffering> userCourseOfferings) {
        this(emailId, userCourseOfferings);
        this.id = id;
    }

    public User(String emailId, List<CourseOffering> userCourseOfferings) {
        this(emailId);
        this.userCourseOfferings = userCourseOfferings;
    }

    public User(User user)
    {
        this(user.getEmailId(), user.getUserCourseOfferings());
        this.id = user.getId();
    }

    public String getEmailId() {
        return emailId;
    }

    public List<CourseOffering> getUserCourseOfferings() {
        return userCourseOfferings;
    }

    private void validateUserEmail(String email)
    {
        if(email.contains(UserValidationValues.EMAIL_SUFFIX_UP.getEmailSuffix()) || email.contains(UserValidationValues.EMAIL_SUFFIX_LOW.getEmailSuffix()))
            return;
        
        throw new InputDataErrorException(AcknowledgementMessages.INPUT_DATA_ERROR.getMessage());
    }

    public void addCourseOffering(CourseOffering courseOffering)
    {
        this.userCourseOfferings.add(courseOffering);
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        
        User user = (User)obj;
        if(user == this)
            return true;
        
        if(this.emailId.equals(user.getEmailId()))
            return true;
        

        return false;
    }

    @Override
    public String toString() {
        return this.emailId.split("@")[0];
    }

    
}
