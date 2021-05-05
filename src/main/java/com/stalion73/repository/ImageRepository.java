package com.stalion73.repository;

import java.util.Optional;

import com.stalion73.model.image.Image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;


public interface ImageRepository extends CrudRepository<Image, Integer> {
    
    Optional<Image> findByName(String name);

}
