package com.stalion73.model;

import java.time.format.DateTimeFormatter;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookingValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Booking.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Booking booking=(Booking) target;
		
		//falta las 2 fechas
		
		
		
	}

}
