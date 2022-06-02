package com.geektrust.backend.entities;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.geektrust.backend.entites.Bogie;
import com.geektrust.backend.entites.ConstantValuesForBogies;
import com.geektrust.backend.entites.Train;
import com.geektrust.backend.exception.InvalidBogieException;
import com.geektrust.backend.repositories.TrainBogieConfigurationRepository;
import com.geektrust.backend.services.TrainServices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("TrainEntiryTest")
@ExtendWith(MockitoExtension.class)
public class TrainEntiryTest {
    

    @Test
    @DisplayName("addBogieToTrain should successfuly add bogie to train")
    public void addBogieToTrainShouldAddBogieTOTrainSuccessfuly()
    {
        List<Bogie> expectedOutput = List.of(new Bogie("CHN", 0, "TRAIN_A"),
                                             new Bogie("SLM", 350, "TRAIN_A"));
        
        try {
            TrainBogieConfigurationRepository trainBogieConfigurationRepository = new TrainBogieConfigurationRepository();
            TrainServices trainService = new TrainServices(trainBogieConfigurationRepository);
            String tarinBogieString = "TRAIN_A ENGINE CHN";

            Train train = trainService.createTain(tarinBogieString);

            Bogie bogie = new Bogie("SLM", 350, "TRAIN_A");

            train.addBogieToTrain(bogie);

            Assertions.assertEquals(expectedOutput, train.getBogies().stream().filter(Predicate.not(bogie_->bogie_.getBogieCode().equals(ConstantValuesForBogies.ENGINE.getBogieValue()))).collect(Collectors.toList()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("addBogieToTrain should throw InvalidBogieException given null bogie")
    public void addBogieToTrainShouldThrowInvalidBogieExceptionGivenNullBogie()
    {
        try {
            TrainBogieConfigurationRepository trainBogieConfigurationRepository = new TrainBogieConfigurationRepository();
            TrainServices trainService = new TrainServices(trainBogieConfigurationRepository);
            String tarinBogieString = "TRAIN_A ENGINE CHN";

            Train train = trainService.createTain(tarinBogieString);

            Assertions.assertThrows(InvalidBogieException.class, ()->train.addBogieToTrain(null));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
