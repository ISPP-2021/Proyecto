package com.stalion73.model.transformer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@Component
public class ServiseDeserializer extends JsonDeserializer<String> {

	@Autowired
	ServiseFormatter formatter;
	
	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String result="";
		try {
			result= p.getText();
		} catch (Exception e) {			
			throw new IOException(e);
		} 
		return result;
	}

}
