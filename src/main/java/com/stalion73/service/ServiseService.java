package com.stalion73.service;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.stalion73.model.Servise;
import com.stalion73.repository.ServiseRepository;

@Service
public class ServiseService {

    ServiseRepository serviseRepository;

    @Autowired
    public ServiseService(ServiseRepository serviseRepository){
        this.serviseRepository = serviseRepository;
    }

    
    @Transactional(readOnly = true)
    public Collection<Servise> findAll(){
      return serviseRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Servise> findById(Integer id){
      return serviseRepository.findById(id);
    }

    @Transactional
    public void save(Servise servise){
        serviseRepository.save(servise);
    }

    @Transactional
    public void deleteById(Integer id){
        serviseRepository.deleteById(id);
    }
    
    @Transactional
    public void delete(Servise servise) {
        serviseRepository.delete(servise);
    }
    
}
