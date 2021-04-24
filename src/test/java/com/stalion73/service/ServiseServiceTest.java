package com.stalion73.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
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
		Assertions.assertThat(servises.isEmpty()).isFalse();
		Assertions.assertThat(servises.size()).isEqualTo(count);
	}

	@Test
	void findServiseByIdTest() {
		Optional<Servise> servise = this.serviseService.findById(1);
		Assertions.assertThat(servise.get().getName()).isEqualTo("Comer");
		Assertions.assertThat(servise.get().getDescription()).isEqualTo("Ven a comer al restaurante y disfruta");
		Assertions.assertThat(servise.get().getPrice()).isEqualTo(20.3);
		Assertions.assertThat(servise.get().getDuration()).isEqualTo(2);
		Assertions.assertThat(servise.get().getCapacity()).isEqualTo(2);
		Assertions.assertThat(servise.get().getDeposit()).isEqualTo(40.7);
		Assertions.assertThat(servise.get().getTax()).isEqualTo(0.05);
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
		Assertions.assertThat(servise.getName()).isEqualTo("Servicio");
		Assertions.assertThat(servise.getDescription()).isEqualTo("Esto es un servicio");
		Assertions.assertThat(servise.getPrice()).isEqualTo(2.5);
		Assertions.assertThat(servise.getDuration()).isEqualTo(10);
		Assertions.assertThat(servise.getCapacity()).isEqualTo(10);
		Assertions.assertThat(servise.getDeposit()).isEqualTo(1.5);
		Assertions.assertThat(servise.getTax()).isEqualTo(0.5);
	}

	@Test
	void deleteServiseByIdTest() {
		Servise servise = this.serviseService.findById(1).get();
		this.serviseService.deleteById(servise.getId());
		List<Servise> servises = (List<Servise>) this.serviseService.findAll();
		Assertions.assertThat(servises.size()).isEqualTo(count - 1);
	}

	@Test
	void deleteServiseTest() {
		Servise servise = this.serviseService.findById(2).get();
		this.serviseService.delete(servise);
		List<Servise> servises = (List<Servise>) this.serviseService.findAll();
		Assertions.assertThat(servises.size()).isEqualTo(count - 1);
	}

}
