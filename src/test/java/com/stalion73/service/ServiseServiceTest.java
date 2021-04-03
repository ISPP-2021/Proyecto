package com.stalion73.service;

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
import com.stalion73.model.Option;
import com.stalion73.model.Servise;
import com.stalion73.model.Supplier;
import com.stalion73.model.User;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ServiseServiceTest {

	@Autowired
	protected ServiseService serviseService;

	private int count;

	@BeforeEach
	void setUp(){
		count = serviseService.findAll().size();
	}

	@Test
	void findAllServiseTest() {
		List<Servise> servises = (List<Servise>) this.serviseService.findAll();
		Assertions.assertTrue(!servises.isEmpty() && servises.size() == count);
	}

	@Test
	void findServiseByIdTest() {
		Optional<Servise> servise = this.serviseService.findById(1);
		Assertions.assertTrue(servise.get().getName().equals("Comer")
				&& servise.get().getDescription().equals("Ven a comer al restaurante y disfruta") && servise.get().getPrice() == 20.3
				&& servise.get().getDuration() == 2 && servise.get().getCapacity() == 2
				&& servise.get().getDeposit() == 40.7 && servise.get().getTax() == 0.05);
	}
	
	@Test
	void saveServiceTest() {
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
		business.setId(3);
		Servise servise = new Servise();
		servise.setId(2);
		servise.setName("Servicio");
		servise.setDescription("Esto es un servicio");
		servise.setPrice(2.5);
		servise.setDuration(10);
		servise.setCapacity(10);
		servise.setDeposit(1.5);
		servise.setTax(0.5);
		servise.setBussiness(business);
		this.serviseService.save(servise);
		Optional<Servise> servises = this.serviseService.findById(2);
		Assertions.assertTrue(!servises.isEmpty() && servise.getName().equals("Servicio"));
	}

	@Test
	void deleteServiseTest() {
		Servise servise = this.serviseService.findById(1).get();
		this.serviseService.delete(servise);
		List<Servise> servises = (List<Servise>) this.serviseService.findAll();
		Assertions.assertTrue(servises.size() == count-1);
	}

	@Test
	void deleteServiseByIdTest() {
		Servise servise = this.serviseService.findById(1).get();
		this.serviseService.deleteById(servise.getId());
		List<Servise> servises = (List<Servise>) this.serviseService.findAll();
		Assertions.assertTrue(servises.size() == count-1);
	}

}
