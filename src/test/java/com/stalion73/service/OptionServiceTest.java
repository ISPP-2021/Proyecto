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

import com.stalion73.model.Option;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OptionServiceTest {

	@Autowired
	private OptionService optionService;

	private int count;

	@BeforeEach
	void setUp() {
		count = optionService.findAll().size();
	}

	@Test
	void findAllOptionsTest() {
		List<Option> options = (List<Option>) this.optionService.findAll();
		Assertions.assertThat(options.isEmpty()).isFalse();
	}

	@Test
	void findOptionByIdTest() {
		Optional<Option> option = this.optionService.findById(1);
		Assertions.assertThat(option.get().getGas()).isEqualTo(3);
		Assertions.assertThat(option.get().getDefaultDeposit()).isEqualTo(0.7);
	}

	@Test
	void saveOptionTest() {
		Option option = new Option();
		option.setId(5);
		option.setAutomatedAccept(true);
		option.setGas(2);
		option.setDefaultDeposit(0.6);
		option.setDepositTimeLimit(4);
		this.optionService.save(option);
		Optional<Option> options = this.optionService.findById(5);
		Assertions.assertThat(options.get().getGas()).isEqualTo(2);
		Assertions.assertThat(options.get().getDefaultDeposit()).isEqualTo(0.6);
		Assertions.assertThat(options.get().getDepositTimeLimit()).isEqualTo(4);
	}

}
