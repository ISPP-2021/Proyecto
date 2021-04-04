package com.stalion73.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.stalion73.model.Business;
import com.stalion73.model.BusinessType;
import com.stalion73.model.Option;
import com.stalion73.model.Supplier;
import com.stalion73.model.User;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BusinessServiceTest {

	@Autowired
	protected BusinessService businessService;

	private int count;

	@BeforeEach
	void setUp(){
		count = businessService.findAll().size();
	}

	@Test
	void findAllBusinessTest() {
		List<Business> business = (List<Business>) this.businessService.findAll();
		Assertions.assertTrue(!business.isEmpty() && business.size() == count);
	}

	@Test
	void findBusinessByIdTest() {
		Optional<Business> business = this.businessService.findById(1);
		Assertions.assertTrue(
				business.get().getName().equals("Pizzeria Gus") && business.get().getAddress().equals("address_1")
						&& business.get().getBusinessType().equals(BusinessType.RESTAURANT));
	}
	
	@Test
	void saveBusinessTest() {
		User user = new User();
		user.setUsername("rafita");
		Supplier supplier = new Supplier();
		supplier.setId(3);
		supplier.setName("Pablo");
		supplier.setLastname("Calvo");
		supplier.setDni("12345678G");
		supplier.setEmail("pablocalvo@gmail.com");
		supplier.setUser(user);
		Option option = new Option();
		option.setId(2);
		option.setAutomatedAccept(true);
		option.setLimitAutomated(2);
		option.setDefaultDeposit(0.6);
		option.setDepositTimeLimit(4);
		Business business = new Business();
		business.setId(4);
		business.setSupplier(supplier);
		business.setOption(option);
		business.setName("Negocio");
		business.setAddress("Calle Calle");
		business.setBusinessType(BusinessType.RESTAURANT);
		business.setAutomatedAccept(true);
		this.businessService.save(business);
		Assertions.assertTrue(business.getName().equals("Negocio") 
				&& business.getAddress().equals("Calle Calle") 
				&& business.getBusinessType().equals(BusinessType.RESTAURANT) 
				&& business.getAutomatedAccept().equals(true));
	}

	@Test
	void deleteBusinessTest() {
		Business business = this.businessService.findById(1).get();
		this.businessService.delete(business);
		List<Business> businessList = (List<Business>) this.businessService.findAll();
		Assertions.assertTrue(businessList.size() == count-1);
	}

	@Test
	void deleteBusinessByIdTest() {
		Business business = this.businessService.findById(1).get();
		this.businessService.deleteById(business.getId());
		List<Business> businessList = (List<Business>) this.businessService.findAll();
		Assertions.assertTrue(businessList.size() == count-1);
	}

	@Test
	void BusinessFindByName() {
		Collection<Business> b = businessService.findBusinessByName("Pizzeria Gus");
		Assertions.assertTrue(b.size() == 1);
	}

	@Test
	void BusinessFindByAddress() {
		Collection<Business> b = businessService.findBusinessByAddress("address_1");
		Assertions.assertTrue(b.size() == 3);
	}

	@Test
	void BusinessFindByType() {
		Collection<Business> b = businessService.findBusinessByType(BusinessType.HAIRDRESSER);
		Assertions.assertTrue(b.size() == 1);
	}

}
