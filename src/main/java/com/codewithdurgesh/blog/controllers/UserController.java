package com.codewithdurgesh.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.AppConstants;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.services.UserServiceI;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserServiceI userservice;
	
	Logger logger=LoggerFactory.getLogger(UserController.class);
	
	/**
	 * @apiNote This api is for creating user 
	 * @author kirti
	 * @param userDto
	 * @return 
	 */
	//post = create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUSer(@Valid @RequestBody UserDto userdto) {
		logger.info("Request entering for create user");
		UserDto createUserdto = this.userservice.createUser(userdto);
		logger.info("Completed Request for create user");
		return new ResponseEntity<>(createUserdto, HttpStatus.CREATED);
	}
	/**
	 * @apiNote This api is for updating user 
	 * @author kirti
	 * @param userDto
	 * @param userId
	 * @return 
	 */
	//put= update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto, @PathVariable Integer userId) {
		logger.info("Request entering for updating user with userId :{}", userId);
		UserDto updatedUser = this.userservice.updateUSer(userdto, userId);
		logger.info("Request completed for updating user with userId :{}", userId);
		return ResponseEntity.ok(updatedUser);
	}
	/**
	 * @apiNote This api is for delete user 
	 * @author kirti
	 * @param userId
	 * @return 
	 */
	//delete = delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer uid) {
		logger.info("Request entering for deleting user with userId :{}", uid);
		this.userservice.deleteUser(uid);
		// return ResponseEntity.ok(Map.of("message","User Deleted successfully"));
		// or
		// return new ResponseEntity(Map.of("message","User Deleted
		// successfully"),HttpStatus.OK);
		// or
		logger.info("Request completed for updating user with userId :{}", uid);
		return new ResponseEntity<String>(AppConstants.USER_DELETE, HttpStatus.OK);
	}
	
	/**
	 * @apiNote This api is for getting all user 
	 * @author kirti
	 * @return 
	 */
	//get = get users all
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		logger.info("Request entering for getting all user  ");
		List<UserDto> allUsers = this.userservice.getAllUsers();
		logger.info("Request completed for getting all user ");
		return new ResponseEntity<List<UserDto>>(allUsers,HttpStatus.FOUND);
	}
	
	/**
	 * @apiNote This api is for getting single user 
	 * @author kirti
	 * @param userId
	 * @return 
	 */	
	//get = get single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		logger.info("Request entering for getting single user with userId : {}", userId);
		UserDto userById = this.userservice.getUserById(userId);
		logger.info("Request completed for getting single user with userId : {}", userId);
		return new ResponseEntity<UserDto>(userById, HttpStatus.FOUND);
	}
	
}
