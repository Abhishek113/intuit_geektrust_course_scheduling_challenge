package com.geektrust.backend.entities;

public class CourseOffering extends BaseEnitiy{

    private String courseName;
    private String author;
    private String date;
    private Integer minimumEmployees, maximumEmployees, totalRegistrations;


    public CourseOffering(String id, String author, String courseName, String date, Integer minimumEmployees, Integer maximumEmployees) {
        this(courseName, author, date, minimumEmployees, maximumEmployees);
        if(id != null)
            this.id = id;
    }

    public CourseOffering(CourseOffering courseOffering)
    {
        this(courseOffering.getId(), courseOffering.getCourseName(), courseOffering.getAuthor(), courseOffering.getDate(), courseOffering.getMinimumEmployees(), courseOffering.getMaximumEmployees());
        this.totalRegistrations = courseOffering.getTotalRegistrations();
    }

    public CourseOffering(String courseName, String author, String date, Integer minimumEmployees, Integer maximumEmployees) {
        this.courseName = courseName;
        this.author = author;
        this.date = date;
        this.minimumEmployees = minimumEmployees;
        this.maximumEmployees = maximumEmployees;
        this.totalRegistrations = 0;
        creatId();
    }

    private void creatId()
    {
        this.id = IdPrefix.COURSE_OFFERING.getPrefixValue() + "-" + this.courseName + "-" + this.author;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
    public Integer getMinimumEmployees() {
        return minimumEmployees;
    }
    public Integer getMaximumEmployees() {
        return maximumEmployees;
    }

    public Integer getTotalRegistrations() {
        return totalRegistrations;
    }

    public void incrementTotalRegistration()
    {
        ++this.totalRegistrations;
    }

    public void decrementTotalRegistration()
    {
        --this.totalRegistrations;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        
        CourseOffering courseOffering = (CourseOffering)obj;
        if(courseOffering == this)
            return true;
        
        if(this.id.equals(courseOffering.getId()))
            return true;
        

        return false;
    }

    @Override
    public String toString() {
        String output = "Course: "+ this.courseName +" id: " + this.id + " Author: " + this.author + " Date: " + this.date;

        return output;
    }
    
}
