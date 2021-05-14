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
import com.stalion73.model.Servise;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ServiseServiceTest {

	@Autowired
	protected ServiseService serviseService;

	private int count;

	@BeforeEach
	void setUp() {
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
				&& servise.get().getDescription().equals("Ven a comer al restaurante y disfruta")
				&& servise.get().getPrice() == 15 && servise.get().getDuration() == 60
				&& servise.get().getCapacity() == 5 && servise.get().getDeposit() == 3
				&& servise.get().getTax() == 0.2);
	}

	@Test
	void saveServiceTest() {
		Business business = new Business();
		business.setId(1);
		Servise servise = new Servise();
		servise.setId(9);
		servise.setName("Servicio");
		servise.setDescription("Esto es un servicio");
		servise.setPrice(2.5);
		servise.setDuration(10);
		servise.setCapacity(10);
		servise.setDeposit(1.5);
		servise.setTax(0.5);
		servise.setBussiness(business);
		this.serviseService.save(servise);
		Assertions.assertTrue(
				servise.getName().equals("Servicio") && servise.getDescription().equals("Esto es un servicio")
						&& servise.getPrice() == 2.5 && servise.getDuration() == 10 && servise.getCapacity() == 10
						&& servise.getDeposit() == 1.5 && servise.getTax() == 0.5);
	}

	@Test
	void deleteServiseByIdTest() {
		Servise servise = this.serviseService.findById(1).get();
		this.serviseService.deleteById(servise.getId());
		List<Servise> servises = (List<Servise>) this.serviseService.findAll();
		Assertions.assertTrue(servises.size() == count - 1);
	}

	@Test
	void deleteServiseTest() {
		Servise servise = this.serviseService.findById(2).get();
		this.serviseService.delete(servise);
		List<Servise> servises = (List<Servise>) this.serviseService.findAll();
		Assertions.assertTrue(servises.size() == count - 1);
	}

}
