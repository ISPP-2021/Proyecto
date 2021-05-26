package com.stalion73.repository;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.image.Image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ImageRepository extends CrudRepository<Image, Integer> {
   
    //@Query("SELECT img FROM Image img WHERE img.name LIKE %:name")
    Optional<Image> findByName(@Param("name") String name);

    @Query("SELECT img FROM Image img WHERE img.business.index =:business")
    Collection<Image> findByBusiness(@Param("business") Integer business );

    @Modifying
    @Query("delete from Image img where img.business.index =:business")
    void deleteAllByBusiness(@Param("business") Integer business);
    // long deleteByTitle(String title);

    @Query("SELECT count(x) FROM Image x")
    Integer tableSize();


}
