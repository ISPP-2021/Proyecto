package com.stalion73.service;

import com.stalion73.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.stalion73.model.Booking;

@Service
public class BookingService {

    BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    @Transactional(readOnly = true)
    public Collection<Booking> findAll(){
      return bookingRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Booking> findById(Integer id){
      return bookingRepository.findById(id);
    }

    @Transactional
    public void save(Booking booking){
        bookingRepository.save(booking);
    }

    @Transactional
    public void deleteById(Integer id){
        bookingRepository.deleteById(id);
    }
    
    @Transactional
    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }
    
}
