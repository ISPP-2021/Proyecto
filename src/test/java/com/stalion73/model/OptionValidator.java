package com.stalion73.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OptionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Option.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Option option = (Option) target;

		if (option.getDefaultDeposit() < 0) {
			errors.rejectValue("defaultDeposit", " must be at least 0", " must be at least 0");

		}
		if (option.getDefaultDeposit() > 1) {
			errors.rejectValue("defaultDeposit", " must be a maximum of 1", " must be a maximum of 1");
		}

		if (option.getDepositTimeLimit() <= 0) {
			errors.rejectValue("depositTimeLimit", " must be positive", " must be positive");
		}
	}

}
