package com.stalion73.service;

import com.stalion73.model.Option;
import com.stalion73.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class OptionService {

    OptionRepository optionRepository;
    //BusinessRepository businessRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository) { this.optionRepository = optionRepository; }

    @Transactional(readOnly = true)
    public Collection<Option> findAll() { return optionRepository.findAll(); }

    @Transactional(readOnly = true)
    public Optional<Option> findById(Integer id){
        return optionRepository.findById(id);
    }

    @Transactional
    public void save(Option option){
        optionRepository.save(option);
    }

    /*    @Transactional
    public void deleteById(Integer id){
        int ID = businessRepository.findBusinessByOptionId(id).getId();
        businessRepository.deleteById(ID);
        optionRepository.deleteById(id);
    }

    @Transactional
    public void delete(Option option) {
        int id = businessRepository.findBusinessBySupplierId(option.getId()).getId();
        businessRepository.deleteById(id);
        optionRepository.delete(option);
    }
 */

    @Transactional
    public void delete(Option option) {
        optionRepository.delete(option);
    }

}
