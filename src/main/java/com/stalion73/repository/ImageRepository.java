package com.stalion73.repository;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.image.Image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;


public interface ImageRepository extends CrudRepository<Image, Integer> {
   
    //@Query("SELECT img FROM Image img WHERE img.name LIKE %:name")
    Optional<Image> findByName(@Param("name") String name);

    @Query("SELECT img FROM Image img WHERE img.business.id LIKE business_id")
    Collection<Image> findByBusiness(@Param("business_id") String business_id );

}
