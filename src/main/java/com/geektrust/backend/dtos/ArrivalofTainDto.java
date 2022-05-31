package com.geektrust.backend.dtos;

import com.geektrust.backend.entites.Bogie;
import com.geektrust.backend.entites.Train;

public class ArrivalofTainDto {

    private final Train train;

    public ArrivalofTainDto(Train train)
    {
        this.train = train;
    }
    
    @Override
    public String toString() {
        String output = "ARRIVAL " + train.getTrainName() + " ";

        for(Bogie bogie: this.train.getBogies())
        {
            output += bogie.getBogieCode() + " ";
        }

        return output.trim();
    }
}
