package com.stalion73.repository;

import java.util.Collection;

import com.stalion73.model.Business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BusinessRepository extends CrudRepository<Business,Integer>{
    Collection<Business> findAll();

    @Query("SELECT b FROM Business b WHERE b.supplier.id =  :supplierId")
    Business findBusinessBySupplierId(@Param("supplierId")Integer supplierId);
    
}
