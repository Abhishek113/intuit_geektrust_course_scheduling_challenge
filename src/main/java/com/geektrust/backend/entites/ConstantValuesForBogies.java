package com.geektrust.backend.entites;

public enum ConstantValuesForBogies {
    ENGINE("ENGINE"), MERGER("HYB"), INPUT_MINIMUM_NUMBER_OF_STRINGS("2");

    private String bogieValue;

    private ConstantValuesForBogies(String bogieValue)
    {
        this.bogieValue = bogieValue;
    }

    public String getBogieValue()
    {
        return this.bogieValue;
    }

    public String getConstantValue()
    {
        return this.bogieValue;
    }
}
