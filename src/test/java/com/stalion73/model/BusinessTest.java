package com.stalion73.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Random;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class BusinessTest {
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	public String generateRandom(Integer length) {
		// Creación de un string aleatorio para una longitud determinada
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		// La descripción tiene que tener un tamaño mínimo de 10 y máximo de 300
		// carácteres
		int targetStringLength = length;
		Random random = new Random();
		String randomString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return randomString;
	}

	@Test
	void shouldNotValidateWithEmptyName() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Business business = new Business();
		business.setName("");
		business.setAddress("address");
		

		Validator validator = createValidator();
		Set<ConstraintViolation<Business>> constraintViolations = validator.validate(business);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Business> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidateWithEmptyAddress() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Business business = new Business();
		business.setName("name");
		business.setAddress("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Business>> constraintViolations = validator.validate(business);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Business> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("address");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}
	@Test
	void shouldNotValidateWithNameLength() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Business business = new Business();
		String name= generateRandom(53);
		business.setName(name);
		business.setAddress("address");
		

		Validator validator = createValidator();
		Set<ConstraintViolation<Business>> constraintViolations = validator.validate(business);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Business> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("length must be between 0 and 50");
	}


}
