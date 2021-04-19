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

public class NamedEntityTest {
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
	void shouldNotValidateWithNameSize() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		NamedEntity namedEntity = new NamedEntity();
		String name=generateRandom(60);
		namedEntity.setName(name);
		

		Validator validator = createValidator();
		Set<ConstraintViolation<NamedEntity>> constraintViolations = validator.validate(namedEntity);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<NamedEntity> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");
	}



}
