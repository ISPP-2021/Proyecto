package com.stalion73.repository;

import java.util.Collection;

import com.stalion73.model.Booking;

import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking,Integer>{
    
    Collection<Booking> findAll();
}
