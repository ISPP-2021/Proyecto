package com.stalion73.repository;

import com.stalion73.model.Option;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface OptionRepository extends CrudRepository<Option, Integer> {

    Collection<Option> findAll();

    Optional<Option> findByIndex(@Param("index") Integer index);

    @Query("SELECT count(x) FROM Option x")
    Integer tableSize();


}
