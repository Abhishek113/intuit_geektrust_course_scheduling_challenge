package com.geektrust.backend.repositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.geektrust.backend.entites.Train;

public class TrainRepository implements CRUDRepository<Train, String>{
    
    private Map<String, Train> trainMap;
    private Integer autoIncrememt = 0;
    
    public TrainRepository()
    {
        this.trainMap = new HashMap<>();
    }

    public TrainRepository(Map<String, Train> trainMap)
    {
        this.trainMap = trainMap;
    }

    @Override
    public void save(Train train) throws IOException{
        if(train.getId() == null)
        {
            ++this.autoIncrememt;
            train = new Train(autoIncrememt.toString(), train.getTrainName(), train.getBogies());
        }
        trainMap.put(train.getId(), train);
    }

    @Override
    public List<Train> findAll() {
        if(trainMap.size() == 0)
            return new ArrayList<>();
        
            return trainMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
    
    @Override
    public Train findById(String id) {
        // TODO implement if needed
        return null;
    }

    @Override
    public void delete(String id) {
        // TODO implement if needed
        
    }
}
