package com.geektrust.backend.entites;

import java.util.Comparator;

public class Bogie extends BaseEntity{
    
    private final String bogieCode;
    private Integer distanceFromSource;
    private final String trainName;

    public Bogie(Bogie bogie)
    {
        this(bogie.getId(), bogie.getBogieCode(), bogie.getDistanceFromSource(), bogie.getTrainName());

    }

    public Bogie(String id, String bogieCode, int distanceFromSource, String trainName)
    {
        this(bogieCode, distanceFromSource, trainName);
        this.id = id;
    }
    public Bogie(String bogieCode, int distanceFromSource, String trainName)
    {
        this.bogieCode = bogieCode;
        this.distanceFromSource = distanceFromSource;
        this.trainName = trainName;
    }

    public String getBogieCode()
    {
        return this.bogieCode;
    }

    public Integer getDistanceFromSource()
    {
        return this.distanceFromSource;
    }

    public void setDistanceFromSource(Integer sourceDistance)
    {
        this.distanceFromSource = sourceDistance;
    }

    public String getTrainName()
    {
        return this.trainName;
    }

    public static SortByDistanceFromSource getSortByDistanceFromSourceClass()
    {
        return new SortByDistanceFromSource();
    }

}

class SortByDistanceFromSource implements Comparator<Bogie>
{

    @Override
    public int compare(Bogie b1, Bogie b2) {
        
        return (int)b1.getDistanceFromSource() - (int)b2.getDistanceFromSource();
    }
}