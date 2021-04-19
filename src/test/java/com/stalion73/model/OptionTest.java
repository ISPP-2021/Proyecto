package com.stalion73.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class OptionTest {
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWithdefaultDepositRange() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Option option= new Option();
		option.setDefaultDeposit(3);
		option.setDepositTimeLimit(2);
		

		Validator validator = createValidator();
		Set<ConstraintViolation<Option>> constraintViolations = validator.validate(option);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Option> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("defaultDeposit");
	}


}
