package com.stalion73.repository;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.*;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ConsumerRepository extends CrudRepository<Consumer, Integer> {

  Collection<Consumer> findAll();

  @Query("SELECT c FROM Consumer c WHERE c.user.username =:username")
  Consumer findConsumerByUsername(String username);

  @Query("SELECT c FROM Consumer c WHERE c.index =:index")
  Optional<Consumer> findByIndex(@Param("index")Integer index);

  @Query("SELECT count(x) FROM Consumer x")
  Integer tableSize();

  @Modifying
	@Query("delete from Consumer c where c.index =:index")
	void deleteByIndex(@Param("index") Integer index);


}
