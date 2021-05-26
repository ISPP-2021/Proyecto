package com.stalion73.service;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.Index;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.stalion73.model.Authorities;
import com.stalion73.model.Consumer;
import com.stalion73.model.User;
import com.stalion73.repository.ConsumerRepository;



@Service
public class ConsumerService {

    ConsumerRepository consumerRepository;

    UserService userService;

    AuthoritiesService authoritiesService;

    
    @Autowired
    public ConsumerService(ConsumerRepository consumerRepository,
                        UserService userService,
                        AuthoritiesService authoritiesService){
        this.consumerRepository = consumerRepository;
        this.userService = userService;
        this.authoritiesService = authoritiesService;
    }
    
    @Transactional(readOnly = true)
    public Collection<Consumer> findAll(){
      return consumerRepository.findAll();
    }

    @Transactional
    public Optional<Consumer> findConsumerByUsername(String username){
        return Optional.ofNullable(this.consumerRepository.findConsumerByUsername(username));
    }
    
    @Transactional(readOnly = true)
    public Optional<Consumer> findByIndex(Integer index){
      return consumerRepository.findByIndex(index);
    }

    @Transactional
    public void save(Consumer consumer){
        Integer size = this.consumerRepository.tableSize();
        consumer.setIndex(size + 1);
        consumerRepository.save(consumer);
        User user = consumer.getUser();
		userService.saveUser(user);
        List<Authorities> authorities = new ArrayList<>(user.getAuthorities());
        Authorities auth = authorities.get(0);
		authoritiesService.saveAuthorities(auth);
    }

    @Transactional
    public void deleteByIndex(Integer index){
        consumerRepository.deleteByIndex(index);
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
