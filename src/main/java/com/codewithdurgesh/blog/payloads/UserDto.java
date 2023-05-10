package com.codewithdurgesh.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	private Integer id;
	
	@NotEmpty
	@Size(min = 4,message = "Username must be minimum 4 characters")
	private String name;
	
	@Email(message = "Email adreess is not valid.!")
	private String email;
	
	@NotEmpty
//	@Size(min = 3, max = 10,message = "Password must be minimum 3 and max 10 characters.")
	@Pattern(regexp = "^[a-z]{5}[0-9]{3}",message = "Password incorrect....")
	private String password;
	
	
	
	@NotEmpty
	private String about;
}
