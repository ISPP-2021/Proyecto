package com.stalion73.repository;

import org.springframework.data.repository.CrudRepository;
import com.stalion73.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
}
