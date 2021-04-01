package com.stalion73.model;

import org.apache.logging.log4j.util.Strings;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BusinessValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Business.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Business business = (Business) target;

		if (Strings.isBlank(business.getName())) {
			errors.rejectValue("name", " must not be blank", " must not be blank");
		}
		if (business.getName().length() > 50) {
			errors.rejectValue("name", " must lower than 50", " must lower than 50");
		}

		if (Strings.isBlank(business.getAddress())) {
			errors.rejectValue("address", " must not be blank", " must not be blank");
		}
		if (business.getAddress().length() > 100) {
			errors.rejectValue("address", " must lower than 100", " must lower than 100");
		}

	}

}
