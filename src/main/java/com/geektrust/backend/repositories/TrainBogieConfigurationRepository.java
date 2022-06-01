package com.geektrust.backend.repositories;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geektrust.backend.utilities.BogieConfigurationPaths;

public class TrainBogieConfigurationRepository implements ITrainBogieConfigurationRepository{
    
    private Map<String, Map<String, Integer>> trainBogieConfiguration;

    public TrainBogieConfigurationRepository() throws JsonParseException, JsonMappingException, IOException, URISyntaxException
    {
        this.loadBogieConfiguration(BogieConfigurationPaths.TRAINs_BOGIE_CONFIG_PATH.getPath());
    }

    public TrainBogieConfigurationRepository(Map<String, Map<String, Integer>> bogieCodeDistanceMap)
    {
        this.trainBogieConfiguration = bogieCodeDistanceMap;
    }

    @Override
    public Optional<Integer> getSourceDistanceOfBogieByCode(String bogieCode, Map<String, Integer> bogieConfig)
    {
        return Optional.ofNullable(bogieConfig.get(bogieCode));
    }

    @Override
    public void save(String trainName, String bogiCode, Integer distanceFromSource)
    {
        this.trainBogieConfiguration.get(trainName).put(bogiCode, distanceFromSource);
    }

    @Override
    public void loadBogieConfiguration(String jsonPath) throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
        
        // URL url = this.getClass().getClassLoader().getResource(jsonPath);

        // File fileObj = new File(url.toURI());

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jsonPath);
        ObjectMapper objectMapper = new ObjectMapper();
        
        // this.trainBogieConfiguration = objectMapper.readValue(fileObj, Map.class);
        this.trainBogieConfiguration = objectMapper.readValue(inputStream, Map.class);

    }

    @Override
    public Map<String, Map<String, Integer>> getTrainBogieConfiguration()
    {
        return this.trainBogieConfiguration;
    }

    @Override
    public List<String> getTrainNames()
    {
        if(this.trainBogieConfiguration.size() == 0)
            return new ArrayList<>();
        
        return this.trainBogieConfiguration.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
