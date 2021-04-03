package com.stalion73.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.stalion73.model.Consumer;
import com.stalion73.model.Supplier;
import com.stalion73.model.User;
import com.stalion73.service.ConsumerService;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ConsumerServiceTest {

	@Autowired
	private ConsumerService consumerService;

	@Test
	void findAllConsumersTest() {
		List<Consumer> consumers = (List<Consumer>) this.consumerService.findAll();
		Assertions.assertTrue(!consumers.isEmpty() && consumers.size() == 2);
	}

	@Test
	void findConsumerByIdTest() {
		Optional<Consumer> consumer = this.consumerService.findById(1);
		Assertions.assertTrue(consumer.get().getName().equals("Jose") && consumer.get().getLastname().equals("Garcia")
				&& consumer.get().getDni().equals("00000000A") && consumer.get().getEmail().equals("cosas@gmail.com")
				&& consumer.get().getUser().getUsername().equals("josito"));
	}
	
	@Test
	void saveConsumerTest() {
		User user = new User();
		user.setUsername("jorge");
		Consumer consumer = new Consumer();
		consumer.setId(3);
		consumer.setName("Jorge");
		consumer.setLastname("Socorro");
		consumer.setDni("62537287P");
		consumer.setEmail("jorgeamigo@gmail.com");
		consumer.setUser(user);
		this.consumerService.save(consumer);
		Optional<Consumer> consumers = this.consumerService.findById(3);
		Assertions.assertTrue(!consumers.isEmpty() && consumers.get().getName().equals("Jorge")
				&& consumers.get().getLastname().equals("Socorro") 
				&& consumers.get().getDni().equals("62537287P")
				&& consumers.get().getEmail().equals("jorgeamigo@gmail.com"));
	}

	@Test
	void deleteConsumerTest() {
		Consumer consumer = this.consumerService.findById(1).get();
		this.consumerService.delete(consumer);
		List<Consumer> consumers = (List<Consumer>) this.consumerService.findAll();
		Assertions.assertTrue(consumers.size() == 1);
	}

	@Test
	void deleteConsumerByIdTest() {
		Consumer consumer = this.consumerService.findById(1).get();
		this.consumerService.deleteById(consumer.getId());
		List<Consumer> consumers = (List<Consumer>) this.consumerService.findAll();
		Assertions.assertTrue(consumers.size() == 1);
	}

}
