package com.geektrust.backend.dtos;

import com.geektrust.backend.entites.Bogie;
import com.geektrust.backend.entites.Train;

public class MergedTrainDto {

    private final Train mergedTrain;

    public MergedTrainDto(Train mergedTrain)
    {
        this.mergedTrain = mergedTrain;
    }

    @Override
    public String toString() {

        String output = "JOURNEY_ENDED";
        if(mergedTrain.getBogies().size() > 2)
        {
            output = "DEPARTURE " + this.mergedTrain.getTrainName() + " ";

            for(Bogie bogie: this.mergedTrain.getBogies())
            {
                output += bogie.getBogieCode() + " ";
            }
        }
        
        return output.trim();
    }
    
}
