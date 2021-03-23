package com.stalion73.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.stalion73.model.Business;
import com.stalion73.repository.BusinessRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    BusinessRepository businessRepository;

    @Autowired
    public BusinessService(BusinessRepository businessRepository){
        this.businessRepository = businessRepository;
    }
    
    @Transactional(readOnly = true)
    public Collection<Business> findAll(){
      return businessRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Business> findById(Integer id){
      return businessRepository.findById(id);
    }

    @Transactional
    public void save(Business business){
        businessRepository.save(business);
    }

    @Transactional
    public void deleteById(Integer id){
        businessRepository.deleteById(id);
    }
    
    @Transactional
    public void delete(Business business) {
        businessRepository.delete(business);
    }
}
