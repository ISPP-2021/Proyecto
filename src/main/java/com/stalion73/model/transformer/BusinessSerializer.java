package com.stalion73.model.transformer;

import java.io.IOException;

import com.stalion73.model.Business;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


public class BusinessSerializer extends JsonSerializer<Business> {
   
    @Override
	public void serialize(Business value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getId().toString());		
	}

}
