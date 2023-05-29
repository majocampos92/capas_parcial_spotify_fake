package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.dtos.LoginDTO;
import com.example.demo.models.dtos.MessageDTO;
import com.example.demo.models.dtos.SaveUserDTO;
import com.example.demo.models.dtos.UpdateDTO;
import com.example.demo.models.dtos.UpdateUsernameDTO;
import com.example.demo.models.entities.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.ErrorHandlers;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/User")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ErrorHandlers ErrorHandler;
	
	@GetMapping("/all")
	public ResponseEntity<?> findAll(){
		List<User> users = userService.findAll();
		return new ResponseEntity<> (users, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> registeruser(@ModelAttribute @Valid SaveUserDTO info, BindingResult validations){
		 
		if(validations.hasErrors()) {
			return new ResponseEntity<>(
					ErrorHandler.mapErrors(validations.getFieldErrors()),
					HttpStatus.BAD_REQUEST
					);
		}
		
		try {
			userService.register(info);
			return new ResponseEntity<>(new MessageDTO("User created"), HttpStatus.CREATED);
		} catch (Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> loginUser(@ModelAttribute @Valid LoginDTO info, BindingResult validations){
		String identifier = info.getIdentifier();
		String password = info.getPassword();
		
		User userfound = userService.AuthUser(identifier, password);
		
		if(userfound == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(userfound, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/update-password")
	public ResponseEntity<?> updatePassword(@ModelAttribute @Valid UpdateDTO info, BindingResult validations){
		String identifier = info.getIdentifier();
		String password = info.getPassword();
		User userlogged = userService.AuthUser(identifier, password);
		
		if(userlogged != null) {
			try {
				userService.UpdatePassword(userlogged, info.getNewPassword());
				return new ResponseEntity<>(new MessageDTO("Password succesfully updated"), HttpStatus.OK);
			} catch (Exception e){
				e.printStackTrace();
				return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity<>(
				ErrorHandler.mapErrors(validations.getFieldErrors()),
				HttpStatus.BAD_REQUEST
				);
		
		
	}
	
	@PostMapping("/change-username")
	public ResponseEntity<?> changeUsername(@ModelAttribute @Valid UpdateUsernameDTO info, BindingResult validations){
		String identifier = info.getIdentifier();
		String password = info.getPassword();
		User userlogged = userService.AuthUser(identifier, password);
		
		if(userlogged != null) {
			try {
				userService.UpdateUsername(userlogged, info.getNewUsername());
				return new ResponseEntity<>(new MessageDTO("Username succesfully updated"), HttpStatus.OK);
			} catch (Exception e){
				e.printStackTrace();
				return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity<>(
				ErrorHandler.mapErrors(validations.getFieldErrors()),
				HttpStatus.BAD_REQUEST
				);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUser(@ModelAttribute @Valid LoginDTO info, BindingResult validations){
		String identifier = info.getIdentifier();
		String password = info.getPassword();
		User userlogged = userService.AuthUser(identifier, password);
		
		if(userlogged != null) {
			try {
				userService.DeletebyId(userlogged.getCode());
				return new ResponseEntity<>(new MessageDTO("User succesfully deleted"), HttpStatus.OK);
			} catch (Exception e){
				e.printStackTrace();
				return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		return new ResponseEntity<>(
				ErrorHandler.mapErrors(validations.getFieldErrors()),
				HttpStatus.BAD_REQUEST
				);
		
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<?> findOneUserByID(@PathVariable String id){
		User userFound = userService.findOneByID(id);
		if(userFound == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<> (userFound, HttpStatus.OK);
	}


}
