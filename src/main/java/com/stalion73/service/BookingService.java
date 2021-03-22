package com.stalion73.service;

import com.stalion73.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.stalion73.model.Booking;
import com.stalion73.repository.ConsumerRepository;

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
