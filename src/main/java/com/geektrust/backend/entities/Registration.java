package com.geektrust.backend.entities;

import java.util.Comparator;

public class Registration extends BaseEnitiy{
    

    private final User user;
    private final CourseOffering courseOffering;
    private RegistrationStatus status;

    private void createId()
    {
        this.id = IdPrefix.REG_COURSE.getPrefixValue() + "-" + this.user.getEmailId().split("@")[0] + "-" + this.courseOffering.getCourseName(); 
    }
    public Registration(User user, CourseOffering courseOffering)
    {
        this.user = user;
        this.courseOffering = courseOffering;
        this.createId();
        this.status = RegistrationStatus.CONFIRMED;
    }

    public Registration(String id, User user, CourseOffering courseOffering)
    {
        this(user, courseOffering);
        if(id != null)
            this.id = id;
    }

    public Registration(Registration registration)
    {
        this(registration.getId(), registration.getUser(), registration.getCourseOffering());
        this.status = registration.getStatus();
    }

    public User getUser() {
        return user;
    }
    public CourseOffering getCourseOffering() {
        return courseOffering;
    }
    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status)
    {
        this.status = status;
    }
    
    @Override
    public String toString() {
        String output = "Registration id: " + this.id + " User: " + user + " course offering: " + this.courseOffering.getCourseName();

        return output;
    }

    public static SortByCourseRegistrationId getSortByCourseRegistrationIdClass()
    {
        return new SortByCourseRegistrationId();
    }

    
}


class SortByCourseRegistrationId implements Comparator<Registration>
{
    @Override
    public int compare(Registration registration1, Registration registration2) {
        return registration1.getId().compareTo(registration2.getId());
    }
}