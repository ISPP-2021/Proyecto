package com.stalion73.repository;

import java.util.Collection;

import com.stalion73.model.Servise;

import org.springframework.data.repository.CrudRepository;

public interface ServiseRepository extends CrudRepository<Servise, Integer>{
    Collection<Servise> findAll();
    
}
