package com.stalion73.repository;

import com.stalion73.model.Option;
import org.springframework.data.repository.CrudRepository;


import java.util.Collection;

public interface OptionRepository extends CrudRepository<Option, Integer> {

    Collection<Option> findAll();
}
