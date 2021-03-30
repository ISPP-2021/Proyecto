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
    public void update(Integer id, Consumer newConsumer){
        Consumer updatedConsumer = this.consumerRepository.findById(id)
                    .map(consumer -> {
                        String name = newConsumer.getName() == null ? consumer.getName() : newConsumer.getName();
                        consumer.setName(name);
                        String lastName = newConsumer.getLastname() == null ? consumer.getLastname() : newConsumer.getLastname();
                        consumer.setLastname(lastName);
                        String dni = newConsumer.getDni() == null ? consumer.getDni() : newConsumer.getDni();
                        consumer.setDni(dni);
                        String email = newConsumer.getEmail() == null ? consumer.getEmail() : newConsumer.getEmail();
                        consumer.setEmail(email);
                        this.consumerRepository.save(consumer);
                        return consumer;
                    })
                    .orElseGet(() -> {
                        return null;
                    });
        this.consumerRepository.save(updatedConsumer);        
    }
    
    @Transactional
    public void delete(Consumer consumer) {
        consumerRepository.delete(consumer);
    }
}
