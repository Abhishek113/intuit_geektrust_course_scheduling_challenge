package com.geektrust.backend.repositories;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ITrainBogieConfigurationRepository {
    
    public void loadBogieConfiguration(String jsonPath) throws JsonParseException, JsonMappingException, IOException;
    public Optional<Integer> getSourceDistanceOfBogieByCode(String bogieCode, Map<String, Integer> trainConfig);
    public void save(String trainName, String bogiCode, Integer distanceFromSource);
    public Map<String, Map<String, Integer>> getTrainBogieConfiguration();
    public List<String> getTrainNames();
}
