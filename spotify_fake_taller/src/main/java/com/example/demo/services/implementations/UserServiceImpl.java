package com.example.demo.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.SaveUserDTO;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void register(SaveUserDTO info) throws Exception {
		
		User user = new User(
					info.getEmail(),
					info.getUsername(),
					info.getPassword()
				);
		
		userRepository.save(user);
	}
	
	@Override
	public List<User> findAll() {
		
		return userRepository.findAll();
	}

	@Override
	public User AuthUser(String identifier, String password) {
		User userbyIdentifier = userRepository.findOneByEmailOrUsername(identifier, identifier);
		User userbyPassword = userRepository.findOneByPassword(password);
		
		if(userbyIdentifier.getCode() == userbyPassword.getCode()){
			return userbyPassword;
		}

		return null;
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void UpdatePassword(User user, String newPassword) throws Exception {
		
		User userToUpdate = userRepository.findOneBycode(user.getCode()) ;
		userToUpdate.setPassword(newPassword);
		userRepository.save(userToUpdate);
		
	}

	@Override
	public void UpdateUsername(User user, String NewUsername) throws Exception {
		
		User userToUpdate = userRepository.findOneBycode(user.getCode()) ;
		userToUpdate.setUsername(NewUsername);
		userRepository.save(userToUpdate);
		
	}

	@Override
	public void DeletebyId(UUID id) throws Exception {
		userRepository.deleteById(id);
	}
	
	
	
}
