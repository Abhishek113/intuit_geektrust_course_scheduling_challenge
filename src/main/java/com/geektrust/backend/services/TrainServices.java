package com.geektrust.backend.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.geektrust.backend.dtos.ArrivalofTainDto;
import com.geektrust.backend.dtos.MergedTrainDto;
import com.geektrust.backend.entites.Bogie;
import com.geektrust.backend.entites.ConstantValuesForBogies;
import com.geektrust.backend.entites.Train;
import com.geektrust.backend.exception.BogieNotFoundException;
import com.geektrust.backend.exception.InvalidInputException;
// import com.geektrust.backend.repositories.CRUDRepository;
// import com.geektrust.backend.repositories.ITrainRepository;
import com.geektrust.backend.repositories.ITrainBogieConfigurationRepository;

public class TrainServices implements ITrainServices{
    
    private final ITrainBogieConfigurationRepository trainBogieConfigurationRepositories;

    public TrainServices(ITrainBogieConfigurationRepository tainBogieConfigurationRepositories)
    {
        this.trainBogieConfigurationRepositories = tainBogieConfigurationRepositories;
        // this.bogieConfigurationRepositories.add(new B)
    }

    @Override
    public Train createTain(String tarinBogieString) throws IOException, BogieNotFoundException, InvalidInputException{

        if(tarinBogieString == null)
            throw new InvalidInputException("At least train name must be provided");
        
        String[] tokens = tarinBogieString.split(" ");

        if(tokens.length < Integer.parseInt(ConstantValuesForBogies.INPUT_MINIMUM_NUMBER_OF_STRINGS.getConstantValue()))
            throw new InvalidInputException("At least train name and ENGINE must be povided");
        
        if(!tokens[1].equals(ConstantValuesForBogies.ENGINE.getBogieValue()))
            throw new InvalidInputException("Wrong ENGINE value");

        String trainName = tokens[0];

        Train train = new Train(trainName);
        Map<String, Map<String, Integer>> trainBogieConfiguration = this.trainBogieConfigurationRepositories.getTrainBogieConfiguration();

        // List<Bogie> bogies = new ArrayList<>();

        for(int i=1; i<tokens.length; ++i)
        {
            String bogieCode = tokens[i];
            
            Bogie currentBogie = null;
            if(bogieCode.equals(ConstantValuesForBogies.ENGINE.getBogieValue()))
            {
                // Integer distance = trainBogieConfiguration.get(trainName).get(ConstantValuesForBogies.TRAIN_A_END.getBogieValue());
                currentBogie = new Bogie(ConstantValuesForBogies.ENGINE.getBogieValue(), 0, train.getTrainName());
            }
            else
            {
                Optional<Integer> sourceDistance = trainBogieConfigurationRepositories.getSourceDistanceOfBogieByCode(bogieCode, trainBogieConfiguration.get(trainName));
                if(sourceDistance.isPresent())
                {
                    currentBogie = new Bogie(bogieCode, sourceDistance.get(), trainName);
                }
                else
                {
                    for(Map.Entry<String, Map<String, Integer>> entry : trainBogieConfiguration.entrySet())
                    {
                        String currentTrainName = entry.getKey();
                        Map<String, Integer> bogieConfiguration = entry.getValue();
                        sourceDistance = trainBogieConfigurationRepositories.getSourceDistanceOfBogieByCode(bogieCode, bogieConfiguration);
                        if(sourceDistance.isPresent())
                        {
                            currentBogie = new Bogie(bogieCode, sourceDistance.get(), currentTrainName);
                        }
                    }
                }
                

                if(currentBogie == null)
                    throw new BogieNotFoundException("Bogie with " + bogieCode + " not found");
            }
            
            train.addBogieToTrain(currentBogie);
        }
        
        return train;
    }

    private Train dropBogiesBeforMergerStation(final Train train, String merger)
    {
        List<Bogie> bogies = train.getBogies();
        Integer mergerDistance = trainBogieConfigurationRepositories.getSourceDistanceOfBogieByCode(merger, 
                                                                    trainBogieConfigurationRepositories.getTrainBogieConfiguration().get(train.getTrainName())).
                                                                    orElseThrow(() -> new BogieNotFoundException("Merger bogie with code " + merger + " not found"));
        //bogies = bogies.stream().filter(b -> b.getTrainName().equals(train.getTrainName())).filter(b -> b.getDistanceFromSource() < mergerDistance).collect(Collectors.toList());
        List<Bogie> temporaryBogieList = new ArrayList<>();

        for(Bogie currentBogie: bogies)
        {
            if(!currentBogie.getBogieCode().equals(ConstantValuesForBogies.ENGINE.getBogieValue()) && currentBogie.getTrainName().equals(train.getTrainName()))
            {
                if(currentBogie.getDistanceFromSource() >= mergerDistance)
                {
                    temporaryBogieList.add(currentBogie);
                }
            }
            else
            {
                temporaryBogieList.add(currentBogie);
            }
        }
        bogies = temporaryBogieList;

        return new Train(train.getId(), train.getTrainName(), bogies);
    }

    private void updatedBogiesSourceDistanceFromNewSource(Train train, Integer newSourceDistance)
    {
        List<Bogie> bogies = train.getBogies();

        for(Bogie bogie: bogies)
        {
            if(!bogie.getBogieCode().equals(ConstantValuesForBogies.ENGINE.getBogieValue()))
            {
                Integer updatedDistance = bogie.getDistanceFromSource()-newSourceDistance;
                bogie.setDistanceFromSource(updatedDistance);
            }
        }

        train.setBogies(bogies);
    }

    @Override
    public Train mergeTwoTrains(Train trainA, Train trainB) throws BogieNotFoundException{

        String merger = ConstantValuesForBogies.MERGER.getBogieValue();

        trainA = dropBogiesBeforMergerStation(trainA, merger);
        ArrivalofTainDto arrivalofTainDto = new ArrivalofTainDto(trainA);
        System.out.println(arrivalofTainDto);

        trainB = dropBogiesBeforMergerStation(trainB, merger);
        arrivalofTainDto = new ArrivalofTainDto(trainB);
        System.out.println(arrivalofTainDto);

        String trainBName = trainB.getTrainName();
        String trainAName = trainA.getTrainName();

        trainA.setBogies(trainA.getBogies().stream().filter(Predicate.not(bogie->bogie.getBogieCode().equals(merger))).collect(Collectors.toList()));
        trainB.setBogies(trainB.getBogies().stream().filter(Predicate.not(bogie->bogie.getBogieCode().equals(merger))).collect(Collectors.toList()));
        
        List<Bogie> trainBbogiesFromA = trainA.getBogies().stream().filter(bogie->bogie.getTrainName().equals(trainBName)).collect(Collectors.toList());
        trainA.setBogies(trainA.getBogies().stream().filter(Predicate.not(bogie->bogie.getTrainName().equals(trainBName))).collect(Collectors.toList()));

        List<Bogie> trainAbogiesFromB = trainB.getBogies().stream().filter(bogie->bogie.getTrainName().equals(trainAName)).collect(Collectors.toList());
        trainB.setBogies(trainB.getBogies().stream().filter(Predicate.not(bogie->bogie.getTrainName().equals(trainAName))).collect(Collectors.toList()));

        trainA.setBogies(Stream.concat(trainA.getBogies().stream(), trainAbogiesFromB.stream()).collect(Collectors.toList()));
        trainB.setBogies(Stream.concat(trainB.getBogies().stream(), trainBbogiesFromA.stream()).collect(Collectors.toList()));

        
        Integer trainAmergerDistance = trainBogieConfigurationRepositories.getSourceDistanceOfBogieByCode(merger, 
                                                                    trainBogieConfigurationRepositories.getTrainBogieConfiguration().get(trainA.getTrainName())).
                                                                    orElseThrow(() -> new BogieNotFoundException("Merger bogie with code " + merger + " not found"));

        updatedBogiesSourceDistanceFromNewSource(trainA, trainAmergerDistance);

        Integer trainBmergerDistance = trainBogieConfigurationRepositories.getSourceDistanceOfBogieByCode(merger, 
                                                                    trainBogieConfigurationRepositories.getTrainBogieConfiguration().get(trainB.getTrainName())).
                                                                    orElseThrow(() -> new BogieNotFoundException("Merger bogie with code " + merger + " not found"));

        updatedBogiesSourceDistanceFromNewSource(trainB, trainBmergerDistance);


        List<Bogie> allEngines = Stream.concat(trainA.getBogies().stream().filter(bogie->bogie.getBogieCode().equals(ConstantValuesForBogies.ENGINE.getBogieValue())), 
                                                trainB.getBogies().stream().filter(bogie->bogie.getBogieCode().equals(ConstantValuesForBogies.ENGINE.getBogieValue()))).collect(Collectors.toList());
                
        List<Bogie> mergedBogies = Stream.concat(trainA.getBogies().stream().filter(Predicate.not(bogie->bogie.getBogieCode().equals(ConstantValuesForBogies.ENGINE.getBogieValue()))), 
                                                trainB.getBogies().stream().filter(Predicate.not(bogie->bogie.getBogieCode().equals(ConstantValuesForBogies.ENGINE.getBogieValue())))).
                                                collect(Collectors.toList());

        Collections.sort(mergedBogies, Bogie.getSortByDistanceFromSourceClass());
        Collections.reverse(mergedBogies);

        String trainABName = "TRAIN_AB";
        Train trainAB = new Train(trainABName, Stream.concat(allEngines.stream(), mergedBogies.stream()).collect(Collectors.toList()));

        return trainAB;
    }


    @Override
    public void printArrivalAndDepartureOuput(String inputFile) throws IOException{
			
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        
        String line = reader.readLine();

        List<Train> trains = new ArrayList<>();
        while(line != null)
        {
            trains.add(createTain(line));
            line = reader.readLine();
        }
        
        
        Train mergedTrain = mergeTwoTrains(trains.get(0), trains.get(1));
        MergedTrainDto mergedTrainDto = new MergedTrainDto(mergedTrain);
        System.out.println(mergedTrainDto);

        reader.close();


    }
}
