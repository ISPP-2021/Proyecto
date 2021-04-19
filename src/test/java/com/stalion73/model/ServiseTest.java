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

public class ServiseTest {
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
		Servise servise = new Servise();
		servise.setName("");
		servise.setDescription("description");
		servise.setPrice(2.0);
		servise.setDuration(5);
		servise.setCapacity(20);
		servise.setDeposit(4.0);
		servise.setTax(5.0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Servise>> constraintViolations = validator.validate(servise);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Servise> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidateWithNameLength() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Servise servise = new Servise();
		String name = generateRandom(53);
		servise.setName(name);
		servise.setDescription("description");
		servise.setPrice(2.0);
		servise.setDuration(5);
		servise.setCapacity(20);
		servise.setDeposit(4.0);
		servise.setTax(5.0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Servise>> constraintViolations = validator.validate(servise);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Servise> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("length must be between 0 and 50");
	}
	@Test
	void shouldNotValidateWithEmptyDescription() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Servise servise= new Servise();
		servise.setName("name");
		servise.setDescription("");
		servise.setPrice(2.0);
		servise.setDuration(5);
		servise.setCapacity(20);
		servise.setDeposit(4.0);
		servise.setTax(5.0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Servise>> constraintViolations = validator.validate(servise);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Servise> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}
	@Test
	void shouldNotValidateWithDescriptionLength() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Servise servise= new Servise();
		String description= generateRandom(260);
		servise.setName("name");
		servise.setDescription(description);
		servise.setPrice(2.0);
		servise.setDuration(5);
		servise.setCapacity(20);
		servise.setDeposit(4.0);
		servise.setTax(5.0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Servise>> constraintViolations = validator.validate(servise);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Servise> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
		assertThat(violation.getMessage()).isEqualTo("length must be between 0 and 255");
	}
	
	@Test
	void shouldNotValidateWithNegativePrice() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Servise servise= new Servise();
		servise.setName("name");
		servise.setDescription("description");
		servise.setPrice(-3.0);
		servise.setDuration(5);
		servise.setCapacity(20);
		servise.setDeposit(4.0);
		servise.setTax(5.0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Servise>> constraintViolations = validator.validate(servise);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Servise> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("price");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");
	}
	@Test
	void shouldNotValidateWithNegativeDuration() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Servise servise= new Servise();
		servise.setName("name");
		servise.setDescription("description");
		servise.setPrice(3.0);
		servise.setDuration(-5);
		servise.setCapacity(20);
		servise.setDeposit(4.0);
		servise.setTax(5.0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Servise>> constraintViolations = validator.validate(servise);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Servise> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("duration");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");
	}
	@Test
	void shouldNotValidateWithNegativeCapacity() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Servise servise= new Servise();
		servise.setName("name");
		servise.setDescription("description");
		servise.setPrice(3.0);
		servise.setDuration(5);
		servise.setCapacity(-20);
		servise.setDeposit(4.0);
		servise.setTax(5.0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Servise>> constraintViolations = validator.validate(servise);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Servise> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("capacity");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");
	}
	@Test
	void shouldNotValidateWithNegativeDeposit() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Servise servise= new Servise();
		servise.setName("name");
		servise.setDescription("description");
		servise.setPrice(3.0);
		servise.setDuration(5);
		servise.setCapacity(20);
		servise.setDeposit(-4.0);
		servise.setTax(5.0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Servise>> constraintViolations = validator.validate(servise);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Servise> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("deposit");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");
	}

}
