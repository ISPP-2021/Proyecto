package com.stalion73.repository;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.Business;
import com.stalion73.model.image.Image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ImageRepository extends CrudRepository<Image, Integer> {
   
    //@Query("SELECT img FROM Image img WHERE img.name LIKE %:name")
    Optional<Image> findByName(@Param("name") String name);

    @Query("SELECT img FROM Image img WHERE img.business.index =:business_index")
    Collection<Image> findByBusiness(@Param("business_index") Integer business_index);

    @Modifying
    @Query("delete from Image img where img.business =:business")
    void deleteAllByBusiness(@Param("business") Business business);
    // long deleteByTitle(String title);

    @Query("SELECT count(x) FROM Image x")
    Integer tableSize();

    @Query("SELECT MAX(index) FROM Image")
    Integer maxIndex();


}
