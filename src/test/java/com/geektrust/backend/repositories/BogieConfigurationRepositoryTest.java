package com.geektrust.backend.repositories;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.geektrust.backend.utilities.BogieConfigurationPaths;

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
        catch(URISyntaxException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
}
