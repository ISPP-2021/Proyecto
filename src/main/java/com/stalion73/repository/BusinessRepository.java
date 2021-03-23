package com.stalion73.repository;

import java.util.Collection;

import com.stalion73.model.Business;

import org.springframework.data.repository.CrudRepository;

public interface BusinessRepository extends CrudRepository<Business,Integer>{
    Collection<Business> findAll();
    
}
