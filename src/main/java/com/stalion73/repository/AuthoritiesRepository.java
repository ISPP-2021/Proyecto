package com.stalion73.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.stalion73.model.Authorities;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{

    @Query("SELECT count(x) FROM Authorities x")
    Integer tableSize();

    @Query("SELECT MAX(index) FROM Authorities")
    Integer maxIndex();
	
}
