package com.stalion73.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.stalion73.model.Consumer;
import com.stalion73.repository.ConsumerRepository;



@Service
public class ConsumerService {

    ConsumerRepository consumerRepository;

    
    @Autowired
    public ConsumerService(ConsumerRepository consumerRepository){
        this.consumerRepository = consumerRepository;
    }
    
    @Transactional(readOnly = true)
    public Collection<Consumer> findAll(){
      return consumerRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Consumer> findById(Integer id){
      return consumerRepository.findById(id);
    }

    @Transactional
    public void save(Consumer consumer){
        consumerRepository.save(consumer);
    }

    @Transactional
    public void deleteById(Integer id){
        consumerRepository.deleteById(id);
    }
    
    @Transactional
    public void delete(Consumer consumer) {
        consumerRepository.delete(consumer);
    }
}
