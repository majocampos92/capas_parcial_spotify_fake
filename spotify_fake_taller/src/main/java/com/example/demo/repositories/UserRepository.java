package com.example.demo.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.example.demo.models.entities.User;

public interface UserRepository 
		extends ListCrudRepository<User, UUID>{
	User findOneBycode(UUID id);
	User findOneByEmailOrUsername(String email, String Username);
	User findOneByPassword(String Password);
}
