package com.stalion73;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.stalion73.model.Servise;
import com.stalion73.service.ServiseService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ServiseServiceTest {

	@Autowired
	protected ServiseService serviseService;

	@Test
	void findAllServiseTest() {
		List<Servise> servises = (List<Servise>) this.serviseService.findAll();
		Assertions.assertTrue(!servises.isEmpty() && servises.size() == 1);
	}

	@Test
	void findServiseByIdTest() {
		Optional<Servise> servise = this.serviseService.findById(1);
		Assertions.assertTrue(servise.get().getName().equals("servise_name")
				&& servise.get().getDescription().equals("servise_description_1") && servise.get().getPrice() == 20.3
				&& servise.get().getDuration() == 2 && servise.get().getCapacity() == 2
				&& servise.get().getDeposit() == 40.7 && servise.get().getTax() == 0.05);
	}

	@Test
	void deleteServiseTest() {
		Servise servise = this.serviseService.findById(1).get();
		this.serviseService.delete(servise);
		List<Servise> servises = (List<Servise>) this.serviseService.findAll();
		Assertions.assertTrue(servises.size() == 0);
	}

	@Test
	void deleteServiseByIdTest() {
		Servise servise = this.serviseService.findById(1).get();
		this.serviseService.deleteById(servise.getId());
		List<Servise> servises = (List<Servise>) this.serviseService.findAll();
		Assertions.assertTrue(servises.size() == 0);
	}

}
