package com.codewithdurgesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.AppConstants;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.repositories.UserRepo;
import com.codewithdurgesh.blog.services.UserServiceI;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	/**
	 * @implNote This method is for creating user
	 * @author kirti
	 * @param userDto
	 * @return 
	 */
	@Override
	public UserDto createUser(UserDto userdto) {
		log.info("Entering DAO call for creating user ");
		User user=this.dtoToUser(userdto);
		User savedUser = this.userRepo.save(user);	
		log.info("Completed DAO call for creating user");
		return this.userToDto(savedUser);
	}
	/**
	 * @implNote This method is for updating user
	 * @author kirti
	 * @param userDto
	 * @param userId
	 * @return 
	 */
	@Override
	public UserDto updateUSer(UserDto userdto, Integer userId) {
		log.info("Entering DAO call for updating user with userId :{} ",userId);

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setAbout(userdto.getAbout());
		User updatedUser = this.userRepo.save(user);
		UserDto userDto2 = this.userToDto(updatedUser);
		log.info("Completeing DAO call for updating user with userId :{} ",userId);
		return userDto2;
	}
	/**
	 * @implNote This method is for getting user by userId
	 * @author kirti
	 * @param userId
	 * @return 
	 */
	@Override
	public UserDto getUserById(Integer userId) {
		log.info("Entering DAO call for getting user with userId :{} ",userId);
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
		log.info("Completed DAO call for getting user with userId :{} ",userId);
		return this.userToDto(user);
	}
	/**
	 * @implNote This method is for getting all user
	 * @author kirti
	 * @return 
	 */
	@Override
	public List<UserDto> getAllUsers() {
		log.info("Entering DAO call for getting all users");
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtolist = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		log.info("Completed DAO call for getting all users ");

		return userDtolist;
	}
	/**
	 * @implNote This method is for deleting user
	 * @author kirti
	 * @param userId
	 * @return 
	 */
	@Override
	public void deleteUser(Integer userId) {
		log.info("Entering DAO call for deleting user with userId :{} ",userId);

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
		
		this.userRepo.delete(user);
		log.info("Completed DAO call for deleting user with userId :{} ",userId);

	}

	public User dtoToUser(UserDto userdto) {
		User user = this.modelMapper.map(userdto, User.class);
//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword());
//		user.setAbout(userdto.getAbout());
		return user;
	}

	public UserDto userToDto(User user) {
		UserDto userdto = this.modelMapper.map(user, UserDto.class);
		return userdto;
	}
}
