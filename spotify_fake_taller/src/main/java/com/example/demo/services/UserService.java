package com.example.demo.services;

import java.util.List;
import java.util.UUID;

import com.example.demo.models.dtos.SaveUserDTO;
import com.example.demo.models.entities.User;

public interface UserService {
	void register (SaveUserDTO info) throws Exception;
	void UpdatePassword (User user, String NewPassword) throws Exception;
	void UpdateUsername (User user, String NewUsername) throws Exception;
	void DeletebyId (UUID id) throws Exception;
	List<User> findAll();
	User AuthUser (String identifier, String password);
	User findOneByID (String id);
}
