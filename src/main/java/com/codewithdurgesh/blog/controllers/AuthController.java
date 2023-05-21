package com.codewithdurgesh.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.exceptions.ApiException;
import com.codewithdurgesh.blog.payloads.AppConstants;
import com.codewithdurgesh.blog.payloads.JwtAuthRequest;
import com.codewithdurgesh.blog.payloads.JwtAuthResponse;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.security.JwtTokenHelper;
import com.codewithdurgesh.blog.services.UserServiceI;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth/")
@Slf4j
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private  UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserServiceI userServiceI;
	
	/**
	 * @apiNote This api is for creating JWT token  
	 * @author kirti
	 * @param request
	 * @return 
	 */
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request ) throws Exception
	{
		log.info("Entering request for creating JWT token ");
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		log.info("Request completed for creating JWT token ");
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
	}
	/**
	 * @apiNote This api is for authenticate JWT token 
	 * @author kirti
	 * @param username
	 * @param password
	 * @return 
	 */
	private void authenticate(String username, String password) throws Exception {
		log.info("Entering request for authenticate JWT token with username and password: {} ",username,password);

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		} catch (BadCredentialsException e) {
//			System.out.println("Invalid details");
			throw new ApiException(AppConstants.INVALID_DETAILS);
		}
		log.info("Completed request for authenticate JWT token with username and password: {} ",username,password);

	}
	/**
	 * @apiNote This api is for registering user 
	 * @author kirti
	 * @param userdto
	 * @return 
	 */
	//register new User api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userdto){
		log.info("Request entering for register User ");
		UserDto registeredUser = this.userServiceI.registerNewUser(userdto);
		log.info("Request completed for register User ");
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}

}

