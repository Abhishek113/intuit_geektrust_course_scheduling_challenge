package com.geektrust.backend.entites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.geektrust.backend.exception.InvalidBogieException;


public class Train extends BaseEntity{
    
    private final String trainName;
    private List<Bogie> bogies;

    public Train(Train train) throws IOException
    {
        this(train.getId(), train.getTrainName(), train.getBogies());
    }

    public Train(String trainName)
    {
        this.trainName = trainName;
        this.bogies = new ArrayList<>();

    }

    public Train(String id, String trainName, List<Bogie> bogies)
    {
        this(trainName, bogies);
        this.id = id;        

    }

    public Train(String trainName, List<Bogie> bogies)
    {
        this(trainName);
        this.bogies = bogies;
    }

    public String getTrainName()
    {
        return this.trainName;
    }

    public List<Bogie> getBogies()
    {
        return this.bogies;
    }

    public void setBogies(List<Bogie> bogies)
    {
        this.bogies = bogies;
    }

    public void addBogieToTrain(Bogie bogie)
    {
        if(bogie == null)
            throw new InvalidBogieException("Can not add null bogie");

        this.bogies.add(bogie);
    }

}
