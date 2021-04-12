package com.stalion73.repository;

import java.util.Collection;
import com.stalion73.model.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ConsumerRepository extends CrudRepository<Consumer, Integer> {

  Collection<Consumer> findAll();

  @Query("SELECT c FROM Consumer c WHERE c.user.username = :username")
  Consumer findConsumerByUsername(String username);

}
