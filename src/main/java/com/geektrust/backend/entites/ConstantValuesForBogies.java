package com.geektrust.backend.entites;

public enum ConstantValuesForBogies {
    ENGINE("ENGINE"), MERGER("HYB"), SPLITTER("BPL"),
    TRAIN_A_START("CHN"), TRAIN_A_END("NDL"), TRAIN_B_START("TVC"), TRAIN_B_END("GHY");

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
