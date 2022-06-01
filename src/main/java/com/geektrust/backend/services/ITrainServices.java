package com.geektrust.backend.services;

import java.io.IOException;
import com.geektrust.backend.entites.Train;
import com.geektrust.backend.exception.BogieNotFoundException;
import com.geektrust.backend.exception.InvalidInputException;

public interface ITrainServices {
    
    public Train mergeTwoTrains(Train trainA, Train trainB) throws BogieNotFoundException;
    public Train createTain(String tarinBogieString) throws IOException, BogieNotFoundException, InvalidInputException;
    public void printArrivalAndDepartureOuput(String inputFile) throws IOException, BogieNotFoundException;
}
