package com.stalion73.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PersonValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;

		if (person.getName().isBlank()) {
			errors.rejectValue("name", " must not be blank", " must not be blank");

		}
		if (person.getName().length() > 20) {
			errors.rejectValue("name", " must be a maximum of 20", " must be a maximum of 20");
		}

		if (person.getLastname().isBlank()) {
			errors.rejectValue("lastName", " must not be blank", " must not be blank");

		}
		if (person.getLastname().length() > 30) {
			errors.rejectValue("lastName", " must be a maximum of 30", " must be a maximum of 30");
		}
		// FALTA DNI

		if (!(person.getEmail().contains("@"))) {
			errors.rejectValue("email", " must contains '@'", " must contains '@'");
		}

	}

}
