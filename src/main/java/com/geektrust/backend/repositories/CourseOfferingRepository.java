package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void delete(CourseOffering entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<CourseOffering> findAll() {
        // TODO Auto-generated method stub
        return null;
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
    
}
