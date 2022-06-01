package com.geektrust.backend.entites;

public enum ConstantValuesForBogies {
    ENGINE("ENGINE"), MERGER("HYB");

    private String bogieValue;

    private ConstantValuesForBogies(String bogieValue)
    {
        this.bogieValue = bogieValue;
    }

    public String getBogieValue()
    {
        return this.bogieValue;
    }
}
