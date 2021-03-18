package com.stalion73.repository;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.*;

import org.springframework.data.repository.CrudRepository;

public interface ConsumerRepository extends CrudRepository<Consumer, Integer> {

  Collection<Consumer> findAll();


}
