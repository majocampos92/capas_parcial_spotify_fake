package com.example.demo.utils;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class ErrorHandlers {

	public Map<String, List<String>> mapErrors(List<FieldError> errors) {
    	Map<String, List<String>> errorsMap = new HashMap<>();
    	
    	errors.stream()
    		.forEach(error -> {
    			List<String> data = errorsMap.get(error.getField());
    			if(data == null) {
    				data = new ArrayList<>();
    			}
    			
    			data.add(error.getDefaultMessage());
    			errorsMap.put(error.getField(), data);
    		});
    	
    	return errorsMap; 
	}
}