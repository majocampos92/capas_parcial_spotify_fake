package com.example.demo.models.dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveUserDTO {
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String email;

	@NotEmpty
	private String password;

}
