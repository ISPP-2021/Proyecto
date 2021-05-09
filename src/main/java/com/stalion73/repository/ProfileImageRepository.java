package com.stalion73.repository;

import java.util.Optional;

import com.stalion73.model.image.ProfileImage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;


public interface ProfileImageRepository extends CrudRepository<ProfileImage, Integer> {
   
    //@Query("SELECT img FROM Image img WHERE img.name LIKE %:name")
    Optional<ProfileImage> findByName(@Param("name") String name);

    @Query("SELECT pic FROM ProfileImage pic WHERE pic.user.username LIKE :username")
    Optional<ProfileImage> findByUsername(@Param("username") String username);

}
