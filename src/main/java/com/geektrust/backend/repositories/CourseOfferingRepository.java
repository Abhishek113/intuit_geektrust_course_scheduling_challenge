package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.geektrust.backend.entities.CourseOffering;

public class CourseOfferingRepository implements ICourseOfferingRepository{

    private final Map<String, CourseOffering> courseOfferingMap;

    public CourseOfferingRepository()
    {
        this.courseOfferingMap = new HashMap<>();
    }

    public CourseOfferingRepository(Map<String, CourseOffering> courseOfferingMap)
    {
        this.courseOfferingMap = courseOfferingMap;
    }

    @Override
    public Optional<CourseOffering> findById(String id) {
        return Optional.ofNullable(this.courseOfferingMap.get(id));
    }

    @Override
    public CourseOffering save(CourseOffering courseOffering) {

        this.courseOfferingMap.put(courseOffering.getId(), courseOffering);

        return courseOffering;
    }

    @Override
    public List<CourseOffering> findAll() {

        if(this.courseOfferingMap.size() == 0)
            return new ArrayList<>();
        
        return this.courseOfferingMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        
    }

    @Override
    public Integer count() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void delete(CourseOffering courseOffering) {
        this.deleteById(courseOffering.getId());  
    }

    @Override
    public void deleteById(String id) {
        if(this.existsById(id))
            this.courseOfferingMap.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        if(this.courseOfferingMap.containsKey(id))
            return true;
        return false;
    }




    
}
