package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.payloads.UserDto;

public interface UserServiceI {
	
	UserDto registerNewUser(UserDto user);

	UserDto createUser(UserDto user);
	
	UserDto updateUSer(UserDto user , Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	
	
	
	
}
