package com.stalion73.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


public class PersonTests {

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
    }
    

    @Test
	void shouldNotValidateWithEmptyfirstName() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setName("");
        person.setLastname("lastName");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
    }

    @Test
	void shouldNotValidateWithEmptylastName() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setName("firstName");
        person.setLastname("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("lastname");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
    }
    
}
