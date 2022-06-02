package com.geektrust.backend.entities;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.geektrust.backend.entites.Bogie;
import com.geektrust.backend.entites.ConstantValuesForBogies;
import com.geektrust.backend.entites.Train;
import com.geektrust.backend.repositories.TrainBogieConfigurationRepository;
import com.geektrust.backend.services.TrainServices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("BogieEntityTest")
@ExtendWith(MockitoExtension.class)
public class BogieEntityTest {

    @Test
    @DisplayName("Bogie object should get created successfuly")
    public void bogieObjectShouldGetSuccessfulyCreated()
    {
        String expectedTrainName = "TRAIN_B", expectedId = "1", expectedBogieCode = "CHN";
        Integer expectedSourceDistance = 2200;

        Bogie bogie = new Bogie("1", "CHN", 2200, "TRAIN_B");

        Assertions.assertEquals(expectedTrainName, bogie.getTrainName());
        Assertions.assertEquals(expectedId, bogie.getId());
        Assertions.assertEquals(expectedBogieCode, bogie.getBogieCode());
        Assertions.assertEquals(expectedSourceDistance, bogie.getDistanceFromSource());
    }

    @Test
    @DisplayName("Bogies of a train should get sorted in ascending order according to source distance")
    public void bogiesOfTrainShouldGetSortedInAscendingOrderAccordingToSourceDistance()
    {
        try {
            TrainBogieConfigurationRepository trainBogieConfigurationRepository = new TrainBogieConfigurationRepository();
            TrainServices trainService = new TrainServices(trainBogieConfigurationRepository);
            String tarinBogieString = "TRAIN_A ENGINE HYB BLR KRN CHN SLM";

            String expectedOutput = "CHN SLM BLR KRN HYB";

            Train train  = trainService.createTain(tarinBogieString);
            train.setBogies(train.getBogies().stream().filter(Predicate.not(bogie->bogie.getBogieCode().equals(ConstantValuesForBogies.ENGINE.getBogieValue()))).collect(Collectors.toList()));
            List<Bogie> trainBogies = train.getBogies();
            Collections.sort(trainBogies, Bogie.getSortByDistanceFromSourceClass());
            String actualOuput = "";

            for(Bogie bogie_: trainBogies)
                actualOuput += bogie_.getBogieCode() + " ";

            Assertions.assertEquals(expectedOutput, actualOuput.trim());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
