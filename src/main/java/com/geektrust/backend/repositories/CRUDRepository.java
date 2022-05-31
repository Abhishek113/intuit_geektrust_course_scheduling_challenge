package com.geektrust.backend.repositories;

import java.io.IOException;
import java.util.List;

public interface CRUDRepository<T, KEY> {
    
    public void save(T entity) throws IOException;
    public List<T> findAll();
    public T findById(KEY id);
    public void delete(KEY id);
}
