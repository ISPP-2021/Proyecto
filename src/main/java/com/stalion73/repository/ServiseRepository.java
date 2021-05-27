package com.stalion73.repository;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.Servise;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ServiseRepository extends CrudRepository<Servise, Integer>{
    Collection<Servise> findAll();

    @Query("SELECT count(x) FROM Servise x")
    Integer tableSize();

    @Query("SELECT MAX(index) FROM Servise")
    Integer maxIndex();

    @Query("SELECT s FROM Servise s WHERE s.index =:index")
    Optional<Servise> findByIndex(@Param("index")Integer index);

    @Modifying
	@Query("delete from Servise s where s.index =:index")
	void deleteByIndex(@Param("index") Integer index);

    
}
