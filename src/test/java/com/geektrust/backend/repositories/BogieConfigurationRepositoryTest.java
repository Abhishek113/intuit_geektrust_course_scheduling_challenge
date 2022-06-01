package com.geektrust.backend.repositories;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.geektrust.backend.exception.BogieNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("BogieConfigurationRepositoryTest")
@ExtendWith(MockitoExtension.class)
public class BogieConfigurationRepositoryTest {


    @Test
    @DisplayName("loadBogieConfiguration function should load all train JSONs correctly")
    public void loadBogieConfigurationShouldLoadJsonCorrectly()
    {
        try
        {
            TrainBogieConfigurationRepository bogieConfigurationRepository = new TrainBogieConfigurationRepository();
            // String jsonPath = BogieConfigurationPaths.TRAINs_BOGIE_CONFIG_PATH.getPath();
            List<String> expectedTrains = List.of("TRAIN_A", "TRAIN_B");
            List<String> expectedTrainABogies = List.of("CHN", "SLM", "BLR", "KRN", "HYB", "NGP", "ITJ", "BPL", "AGA", "NDL");
        
            //bogieConfigurationRepository.loadBogieConfiguration("trainsBogiesConfigurations.json");
            Map<String, Map<String, Integer>> trainBogieConfiguration = bogieConfigurationRepository.getTrainBogieConfiguration();
            Assertions.assertEquals(expectedTrains, bogieConfigurationRepository.getTrainNames());
            Assertions.assertEquals(expectedTrainABogies, trainBogieConfiguration.get("TRAIN_A").entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()));

        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        } 
        
    }

    @Test
    @DisplayName("getSourceDistanceOfBogieByCode should return correct source distace given valid bogie code Train A")
    public void getSourceDistanceOfBogieByCodeShouldReturnCorrectSourceCodeGivenValidBogiecodeForTrainA()
    {
        try {
            TrainBogieConfigurationRepository trainBogieConfigurationRepository = new TrainBogieConfigurationRepository();
            Map<String, Map<String, Integer>> trainBogieConfiguration = trainBogieConfigurationRepository.getTrainBogieConfiguration();

            String bogieCode = "AGA", trainName = "TRAIN_A";
            Integer expectedOutput = 2500;

            Optional<Integer> distance = trainBogieConfigurationRepository.getSourceDistanceOfBogieByCode(bogieCode, trainBogieConfiguration.get(trainName));
            
            Assertions.assertEquals(expectedOutput, distance.get());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("getSourceDistanceOfBogieByCode should return correct source distace given valid bogie code Train B")
    public void getSourceDistanceOfBogieByCodeShouldReturnCorrectSourceCodeGivenValidBogiecodeForTrainB()
    {
        try {
            TrainBogieConfigurationRepository trainBogieConfigurationRepository = new TrainBogieConfigurationRepository();
            Map<String, Map<String, Integer>> trainBogieConfiguration = trainBogieConfigurationRepository.getTrainBogieConfiguration();

            String bogieCode = "PTA", trainName = "TRAIN_B";
            Integer expectedOutput = 3800;

            Optional<Integer> distance = trainBogieConfigurationRepository.getSourceDistanceOfBogieByCode(bogieCode, trainBogieConfiguration.get(trainName));
            
            Assertions.assertEquals(expectedOutput, distance.get());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("getSourceDistanceOfBogieByCode should raise BogieNotFoundException if bogie not associated with train")
    public void getSourceDistanceOfBogieByCodeShouldRaiseExceptionOfBogieNotFound()
    {
        try {
            TrainBogieConfigurationRepository trainBogieConfigurationRepository = new TrainBogieConfigurationRepository();
            Map<String, Map<String, Integer>> trainBogieConfiguration = trainBogieConfigurationRepository.getTrainBogieConfiguration();

            String bogieCode = "SLM", trainName = "TRAIN_B";
            
            Assertions.assertThrows(BogieNotFoundException.class, ()->trainBogieConfigurationRepository.getSourceDistanceOfBogieByCode(bogieCode, trainBogieConfiguration.get(trainName)).orElseThrow(()->new BogieNotFoundException()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
