package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.geektrust.backend.entities.User;

public class UserRepository implements IUserRepository{

    private final Map<String, User> userMap;
    // private final Integer autoIncrement = 0;

    public UserRepository()
    {
        this.userMap = new HashMap<>();
    }

    public UserRepository(Map<String, User> userMap)
    {
        this.userMap = userMap;
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void delete(User entity) {
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
    public List<User> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public User save(User user) {

        userMap.put(user.getEmailId(), user);

        return user;
    }
    
    
}   
