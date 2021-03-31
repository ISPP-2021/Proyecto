package com.stalion73;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.stalion73.model.Option;
import com.stalion73.service.OptionService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OptionServiceTest {

	@Autowired
	private OptionService optionService;

	@Test
	void findAllOptionsTest() {
		List<Option> options = (List<Option>) this.optionService.findAll();
		Assertions.assertTrue(!options.isEmpty() && options.size() == 1);
	}

	@Test
	void findOptionByIdTest() {
		Optional<Option> option = this.optionService.findById(1);
		Assertions.assertTrue(option.get().getLimitAutomated() == 3 && option.get().getDefaultDeposit() == 0.7
				&& option.get().getDepositTimeLimit() == 5);
	}

//	@Test
//	void deleteOptionTest() {
//		Option option = this.optionService.findById(1).get();
//		this.optionService.delete(option);
//		List<Option> options = (List<Option>) this.optionService.findAll();
//		Assertions.assertTrue(options.size() == 1);
//	}
//
//	@Test
//	void deleteOptionByIdTest() {
//		Option option = this.optionService.findById(1).get();
//		this.optionService.deleteById(option.getId());
//		List<Option> options = (List<Option>) this.optionService.findAll();
//		Assertions.assertTrue(options.size() == 1);
//	}

}
