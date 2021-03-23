package com.stalion73.service;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.Supplier;
import com.stalion73.repository.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierService {
    
    SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    public Collection<Supplier> findAll(){
      return supplierRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Supplier> findById(Integer id){
      return supplierRepository.findById(id);
    }

    @Transactional
    public void save(Supplier supplier){
        supplierRepository.save(supplier);
    }

    @Transactional
    public void deleteById(Integer id){
        supplierRepository.deleteById(id);
    }
    
    @Transactional
    public void delete(Supplier supplier) {
        supplierRepository.delete(supplier);
    }
}
