package com.stalion73.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.stalion73.model.Supplier;
import com.stalion73.model.Booking;
import com.stalion73.model.Business;
import com.stalion73.model.Servise;
import com.stalion73.model.SubscriptionType;
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

    @Transactional(readOnly = true)
    public void deleteById(Integer id){
        supplierRepository.deleteById(id);
    }

    @Transactional
    public Optional<Supplier> findSupplierByUsername(String username){
        return Optional.ofNullable(this.supplierRepository.findSupplierByUsername(username));
    }

    @Transactional
    public Optional<Booking> findBookingOnSupplier(Supplier supplier, Integer id){
        Set<Business> business = supplier.getBusiness();
        for(Business b : business){
            Set<Servise> servises = b.getServices();
            for(Servise s : servises){
                Set<Booking> bookings = s.getBookings();
                for(Booking book : bookings){
                    if(book.getId().equals(id)){
                        return Optional.of(book);
                    }
                }
            }
        }
        return Optional.empty();

    }

    @Transactional
    public void save(Supplier supplier){
        if(supplier.getSubscription()==null){
            
            supplier.setSubscription(SubscriptionType.FREE);
        }
        supplierRepository.save(supplier);
    }

    
    //Que sentido tiene este metodo? solo borra negocios no borra supplier
    //Modificado por rodri
    /*public void deleteById(Integer id){
        int newId = businessRepository.findBusinessBySupplierId(id).getId();
        businessRepository.deleteById(newId);

    }*/
    
    public void deleteBusinessBySupplierId(Integer id){
        businessRepository.findBusinessBySupplierId(id).stream()
                                                        .forEach(business -> {
                                                        businessRepository.deleteById(business.getId());
                                                        });
    }

    @Transactional
    public void delete(Supplier supplier) {
        businessRepository.findBusinessBySupplierId(supplier.getId()).stream()
                                        .forEach(business -> {
                                         businessRepository.deleteById(business.getId());
                                         });
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
