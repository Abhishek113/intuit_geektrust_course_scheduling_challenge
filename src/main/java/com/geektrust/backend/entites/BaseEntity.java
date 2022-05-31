package com.geektrust.backend.entites;

public class BaseEntity {

    protected String id;

    // public BaseEntity(String id)
    // {
    //     this.id = id;
    // }

    public String getId()
    {
        return this.id;
    }

    @Override
    public String toString() {
        return "id: "+this.id;
    }
    
}
