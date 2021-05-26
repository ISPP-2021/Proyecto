package com.stalion73.repository;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.Booking;
import com.stalion73.model.Status;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends CrudRepository<Booking,Integer>{
    
    Collection<Booking> findAll();
    
    
	@Query("SELECT DISTINCT booking FROM Booking booking WHERE booking.status LIKE :status")
	Collection<Booking> findBookingByStatus(@Param("status") Status status);

	@Query("SELECT b FROM Booking b WHERE b.index =:index")
    Optional<Booking> findByIndex(@Param("index")Integer index);

	@Query("SELECT count(x) FROM Booking x")
    Integer tableSize();

	@Modifying
	@Query("delete from Booking b where b.index =:index")
	void deleteByIndex(@Param("index") Integer index);
}
