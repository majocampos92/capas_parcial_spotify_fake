package com.example.demo.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
	
	@NotEmpty
	private String identifier;
	
	@NotEmpty
	private String password;
}
