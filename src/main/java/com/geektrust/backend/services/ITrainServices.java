package com.geektrust.backend.services;

import java.io.IOException;
import java.util.List;

import com.geektrust.backend.entites.Train;

public interface ITrainServices {
    
    public Train mergeTwoTrains(Train trainA, Train trainB);
    public Train createTain(String tarinBogieString) throws IOException;
    public List<Train> splitTrains(Train train);
    public void printArrivalAndDepartureOuput(String inputFile);
}
