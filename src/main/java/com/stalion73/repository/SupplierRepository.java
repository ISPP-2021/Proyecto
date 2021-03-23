package com.stalion73.repository;

import java.util.Collection;

import com.stalion73.model.Supplier;

import org.springframework.data.repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<Supplier, Integer>{
    Collection<Supplier> findAll();
    
}
