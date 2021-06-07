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

import com.stalion73.model.Consumer;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ConsumerServiceTest {

	@Autowired
	private ConsumerService consumerService;

	private int count;

	@BeforeEach
	void setUp() {
		count = consumerService.findAll().size();
	}

	@Test
	void findAllConsumersTest() {
		List<Consumer> consumers = (List<Consumer>) this.consumerService.findAll();
		Assertions.assertTrue(!consumers.isEmpty() && consumers.size() == count);
	}

	@Test
	void findConsumerByIdTest() {
		Optional<Consumer> consumer = this.consumerService.findByIndex(1);
		Assertions.assertTrue(consumer.get().getName().equals("José") && consumer.get().getLastname().equals("García")
				&& consumer.get().getDni().equals("23487343A") && consumer.get().getEmail().equals("josito@gmail.com")
				&& consumer.get().getUser().getUsername().equals("josito"));
	}

	@Test
	void findConsumerByUsernameTest() {
		Optional<Consumer> consumer = consumerService.findConsumerByUsername("josito");
		Assertions.assertTrue(consumer.get().getName().equals("José") && consumer.get().getLastname().equals("García")
				&& consumer.get().getDni().equals("23487343A") && consumer.get().getEmail().equals("josito@gmail.com"));
	}

//	@Test
//	void saveConsumerTest() {
//		
//	}

	@Test
	void deleteConsumerByIdTest() {
		Consumer consumer = this.consumerService.findByIndex(1).get();
		this.consumerService.deleteByIndex(consumer.getIndex());
		List<Consumer> consumers = (List<Consumer>) this.consumerService.findAll();
		Assertions.assertTrue(consumers.size() == count - 1);
	}

	@Test
	void deleteConsumerTest() {
		Consumer consumer = this.consumerService.findByIndex(2).get();
		this.consumerService.delete(consumer);
		List<Consumer> consumers = (List<Consumer>) this.consumerService.findAll();
		Assertions.assertTrue(consumers.size() == count - 1);
	}

}
