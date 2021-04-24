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
		Assertions.assertThat(consumers.isEmpty()).isFalse();
		Assertions.assertThat(consumers.size()).isEqualTo(count);
	}

	@Test
	void findConsumerByIdTest() {
		Optional<Consumer> consumer = this.consumerService.findById(1);
		Assertions.assertThat(consumer.get().getName()).isEqualTo("Jose");
		Assertions.assertThat(consumer.get().getLastname()).isEqualTo("Garcia");
		Assertions.assertThat(consumer.get().getDni()).isEqualTo("23487343A");
		Assertions.assertThat(consumer.get().getEmail()).isEqualTo("cosas@gmail.com");
		Assertions.assertThat(consumer.get().getUser().getUsername()).isEqualTo("josito");
	}

	@Test
	void findConsumerByUsernameTest() {
		Optional<Consumer> consumer = consumerService.findConsumerByUsername("josito");
		Assertions.assertThat(consumer.get().getName()).isEqualTo("Jose");
		Assertions.assertThat(consumer.get().getLastname()).isEqualTo("Garcia");
		Assertions.assertThat(consumer.get().getDni()).isEqualTo("23487343A");
		Assertions.assertThat(consumer.get().getEmail()).isEqualTo("cosas@gmail.com");
	}

//	@Test
//	void saveConsumerTest() {
//		
//	}

	@Test
	void deleteConsumerByIdTest() {
		Consumer consumer = this.consumerService.findById(1).get();
		this.consumerService.deleteById(consumer.getId());
		List<Consumer> consumers = (List<Consumer>) this.consumerService.findAll();
		Assertions.assertThat(consumers.size()).isEqualTo(count - 1);
	}

	@Test
	void deleteConsumerTest() {
		Consumer consumer = this.consumerService.findById(2).get();
		this.consumerService.delete(consumer);
		List<Consumer> consumers = (List<Consumer>) this.consumerService.findAll();
		Assertions.assertThat(consumers.size()).isEqualTo(count - 1);
	}

}
