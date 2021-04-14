package com.stalion73.repository;

import java.util.Collection;

import com.stalion73.model.Booking;
import com.stalion73.model.Status;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends CrudRepository<Booking,Integer>{
    
    Collection<Booking> findAll();
    
    
	@Query("SELECT DISTINCT booking FROM Booking booking WHERE booking.status LIKE :status")
	Collection<Booking> findBookingByStatus(@Param("status") Status status);
}
