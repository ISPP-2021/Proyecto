package com.stalion73.model.transformer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class BusinessDeserializer extends JsonDeserializer<Integer> {
    
	@Autowired
	ServiseFormatter formatter;
	
	@Override
	public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Integer result= null;
		try {
			result= Integer.parseInt(p.getText());
		} catch (Exception e) {			
			throw new IOException(e);
		} 
		return result;
	}

}
