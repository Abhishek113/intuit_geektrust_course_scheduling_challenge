package com.geektrust.backend.entities;

public enum IdPrefix {
    
    REG_COURSE("REG-COURSE"), COURSE_OFFERING("OFFERING");

    private String prefix;

    private IdPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getPrefixValue()
    {
        return this.prefix;
    }
}
