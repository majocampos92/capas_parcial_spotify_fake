package com.example.demo.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateDTO {
	@NotEmpty
	private String identifier;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String newPassword;
	
}
