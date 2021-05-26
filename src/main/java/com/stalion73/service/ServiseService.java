package com.stalion73.service;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<Servise> findByIndex(Integer index){
      return serviseRepository.findByIndex(index);
    }

    @Transactional
    public void save(Servise servise){
        Integer size = this.serviseRepository.tableSize();
        servise.setIndex(size + 1);
        serviseRepository.save(servise);
    }

    @Transactional
    public void update(Integer id, Servise newServise){
        assertNotNull(this.serviseRepository.findById(id).get());
        Servise updatedServise = this.serviseRepository.findById(id).map(servise -> {
			String name = newServise.getName() == null ? servise.getName() : newServise.getName();
			servise.setName(name);
			String description = newServise.getDescription() == null ? servise.getDescription()
					: newServise.getDescription();
			servise.setDescription(description);
			Double price = newServise.getPrice() == null ? servise.getPrice() : newServise.getPrice();
			servise.setPrice(price);
			Integer duration = newServise.getDuration() == null ? servise.getDuration() : newServise.getDuration();
			servise.setDuration(duration);
			Integer capacity = newServise.getCapacity() == null ? servise.getCapacity() : newServise.getCapacity();
			servise.setCapacity(capacity);
			Double deposit = newServise.getDeposit() == null ? servise.getDeposit() : newServise.getDeposit();
			servise.setDeposit(deposit);
			Double tax = newServise.getTax() == null ? servise.getTax() : newServise.getTax();
			servise.setTax(tax);
			this.serviseRepository.save(servise);
			return servise;
		}).orElseGet(() -> {
			return null;
		});
        this.serviseRepository.save(updatedServise);
    }

    @Transactional
    public void deleteByIndex(Integer id){
        serviseRepository.deleteByIndex(id);
    }
    
    @Transactional
    public void delete(Servise servise) {
        serviseRepository.delete(servise);
    }
    
}
