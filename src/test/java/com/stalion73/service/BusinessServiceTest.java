package com.stalion73.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.stalion73.model.Business;
import com.stalion73.model.BusinessType;

import com.stalion73.service.BusinessService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BusinessServiceTest {

	@Autowired
	protected BusinessService businessService;

	@Test
	void findAllBusinessTest() {
		List<Business> business = (List<Business>) this.businessService.findAll();
		Assertions.assertTrue(!business.isEmpty() && business.size() == 3);
	}

	@Test
	void findBusinessByIdTest() {
		Optional<Business> business = this.businessService.findById(1);
		Assertions.assertTrue(
				business.get().getName().equals("business_name_1") && business.get().getAddress().equals("address_1")
						&& business.get().getBusinessType().equals(BusinessType.HAIRDRESSER));
	}

//	@Test
//	void deleteBusinessTest() {
//		Business business = this.businessService.findById(1).get();
//		this.businessService.delete(business);
//		List<Business> businessList = (List<Business>) this.businessService.findAll();
//		Assertions.assertTrue(businessList.size() == 2);
//	}
//
//	@Test
//	void deleteBusinessByIdTest() {
//		Business business = this.businessService.findById(1).get();
//		this.businessService.deleteById(business.getId());
//		List<Business> businessList = (List<Business>) this.businessService.findAll();
//		Assertions.assertTrue(businessList.size() == 2);
//	}

	@Test
	void BusinessFindByName() {
		Collection<Business> b = businessService.findBusinessByName("business_name_1");
		Assertions.assertTrue(b.size() == 1);
	}

	@Test
	void BusinessFindByAddress() {
		Collection<Business> b = businessService.findBusinessByAddress("address_2");
		Assertions.assertTrue(b.size() == 2);
	}

	@Test
	void BusinessFindByType() {
		Collection<Business> b = businessService.findBusinessByType(BusinessType.HAIRDRESSER);
		Assertions.assertTrue(b.size() == 2);
	}

}
