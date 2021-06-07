package com.stalion73.service;


import java.util.Collection;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.stalion73.model.*;
import com.stalion73.repository.BusinessRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;

@Service
public class BusinessService {

	BusinessRepository businessRepository;

	@Autowired
	public BusinessService(BusinessRepository businessRepository) {
		this.businessRepository = businessRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Business> findAll() {
		return businessRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Business> findByIndex(Integer index) {
		return businessRepository.findByIndex(index);
	}

	@Transactional
	public void save(Business business) {
		Integer index = business.getIndex() != null ? business.getIndex() : this.businessRepository.maxIndex() + 1;
		business.setIndex(index);
		businessRepository.save(business);
	}

	@Transactional
	public void deleteByIndex(Integer index) {
		businessRepository.deleteByIndex(index);
	}

	@Transactional
	public void delete(Business business) {
		businessRepository.delete(business);
	}

	@Transactional
	public void update(Integer index, Business newBusiness) {
		// business(name, address, businessType, automatedAccept, Supplier, Servises)
		Business updatedBusiness = this.businessRepository.findByIndex(index).map(business -> {
			String name = newBusiness.getName() == null ? business.getName() : newBusiness.getName();
			business.setName(name);
			String address = newBusiness.getAddress() == null ? business.getAddress() : newBusiness.getAddress();
			business.setAddress(address);
			LocalTime openTime = newBusiness.getOpenTime() == null ? business.getOpenTime() : newBusiness.getOpenTime();
			business.setOpenTime(openTime);
			LocalTime closeTime = newBusiness.getCloseTime() == null ? business.getCloseTime() : newBusiness.getCloseTime();
			business.setCloseTime(closeTime);
			BusinessType type = newBusiness.getBusinessType() == null ? business.getBusinessType()
					: newBusiness.getBusinessType();
			business.setBusinessType(type);
			Option option = newBusiness.getOption() == null ? business.getOption() : newBusiness.getOption();
			business.setOption(option);
			Boolean automatedAccept = newBusiness.getAutomatedAccept() == null ? business.getAutomatedAccept()
					: newBusiness.getAutomatedAccept();
			business.setAutomatedAccept(automatedAccept);
			this.businessRepository.save(business);
			return business;
		}).orElseGet(() -> {
			return null;
		});
		this.businessRepository.save(updatedBusiness);
	}

	@Transactional
	public Collection<Business> findBusinessByName(String name) {
		return businessRepository.findBusinessByName(name);

	}

	@Transactional
	public Collection<Business> findBusinessByAddress(String address) {
		return businessRepository.findBusinessByAddress(address);
	}

	@Transactional
	public Collection<Business> findBusinessByType(BusinessType type) {
		return	businessRepository.findBusinessByType(type);
	}

    @Transactional
    public Collection<Business> findBusinessBySupplierIndex(Integer supplierIndex) {
        return this.businessRepository.findBusinessBySupplierIndex(supplierIndex);
    }
}
