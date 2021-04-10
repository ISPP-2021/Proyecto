package com.stalion73.model.transformer;


import java.io.IOException;

import com.stalion73.model.Servise;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
public class ServiseSerializer extends JsonSerializer<Servise> {

	@Override
	public void serialize(Servise value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getId().toString());		
	}

}