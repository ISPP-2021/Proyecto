package com.stalion73.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ServiseValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Servise.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Servise servise= (Servise) target;
		
		if(servise.getName().isBlank()) {
			errors.rejectValue("name", " must not be blank", " must not be blank");
		}
		if(servise.getName().length()>50) {
			errors.rejectValue("name", " must be a maximum of 50", " must be a maximum of 50");
		}
		if(servise.getDescription().isBlank()) {
			errors.rejectValue("description", " must not be blank", " must not be blank");
		}
		if(servise.getDescription().length()>255) {
			errors.rejectValue("description", " must be a maximum of 255", " must be a maximum of 255");
		}
		if(servise.getPrice()<=0) {
			errors.rejectValue("price", " must be positive", " must be positive");
		}
		if(servise.getDuration()<0) {
			errors.rejectValue("duration", " must be positive or zero", " must be positive or zero");
		}
		if(servise.getCapacity()<0) {
			errors.rejectValue("capacity", " must be positive or zero", " must be positive or zero");
		}
		if(servise.getDeposit()<0) {
			errors.rejectValue("deposit", " must be positive or zero", " must be positive or zero");
		}
		
	}

}
