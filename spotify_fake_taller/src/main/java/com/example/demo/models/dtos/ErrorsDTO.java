package com.example.demo.models.dtos;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorsDTO {

	private Map<String, List<String>> errors;
	
	public ErrorsDTO(Map<String, List<String>> mapErrors) {
		// TODO Auto-generated constructor stub
	}

	public Map<String, List<String>> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, List<String>> errors) {
		this.errors = errors;
	}
}
