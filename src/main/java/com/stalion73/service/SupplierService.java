package com.stalion73.service;

import java.util.Collection;
import java.util.Optional;

import com.stalion73.model.Supplier;
import com.stalion73.repository.BusinessRepository;
import com.stalion73.repository.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierService {

    SupplierRepository supplierRepository;
    BusinessRepository businessRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, BusinessRepository businessRepository){
        this.supplierRepository = supplierRepository;
        this.businessRepository = businessRepository;
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
    public Optional<Supplier> findSupplierByUsername(String username){
        return Optional.ofNullable(this.supplierRepository.findSupplierByUsername(username));
    }

    @Transactional
    public void save(Supplier supplier){
        supplierRepository.save(supplier);
    }

    @Transactional
    public void deleteById(Integer id){
        int newId = businessRepository.findBusinessBySupplierId(id).getId();
        businessRepository.deleteById(newId);
    }

    @Transactional
    public void delete(Supplier supplier) {
        int id = businessRepository.findBusinessBySupplierId(supplier.getId()).getId();
        businessRepository.deleteById(id);
        supplierRepository.delete(supplier);
    }

    @Transactional
    public void update(Integer id, Supplier newSupplier){
        Supplier updatedSupplier = this.supplierRepository.findById(id)
                    .map(supplier -> {
                            String name = newSupplier.getName() == null ? supplier.getName() : newSupplier.getName();
                            supplier.setName(name);
                            String lastName = newSupplier.getLastname() == null ? supplier.getLastname() : newSupplier.getLastname();
                            supplier.setLastname(lastName);
                            String dni = newSupplier.getDni() == null ? supplier.getDni() : newSupplier.getDni();
                            supplier.setDni(dni);
                            String email = newSupplier.getEmail() == null ? supplier.getEmail() : newSupplier.getEmail();
                            supplier.setEmail(email);
                            this.supplierRepository.save(supplier);
                            return supplier;
                        }
                    ) 
                    .orElseGet(() -> {
                        newSupplier.setId(id);
                        this.supplierRepository.save(newSupplier);
                        return newSupplier;
                    });
        this.supplierRepository.save(updatedSupplier);
    }
}
