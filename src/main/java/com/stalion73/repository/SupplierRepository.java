package com.stalion73.repository;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.Supplier;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends CrudRepository<Supplier, Integer>{
    Collection<Supplier> findAll();

    @Query("SELECT s FROM Supplier s WHERE s.user.username = :username")
    Supplier findSupplierByUsername(String username);

    @Query("SELECT s FROM Supplier s WHERE s.index = :index")
    Optional<Supplier> findByIndex(@Param("index")Integer index);

    @Query("SELECT count(x) FROM Supplier x")
    Integer tableSize();

    @Query("SELECT MAX(index) FROM Supplier")
    Integer maxIndex();

    @Modifying
	@Query("delete from Supplier s where s.index =:index")
	void deleteByIndex(@Param("index") Integer index);
    
}
