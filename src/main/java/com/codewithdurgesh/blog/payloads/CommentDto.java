package com.codewithdurgesh.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	
	private Integer id;
	
	@NotBlank
	@Size(min = 20,message = "Minimum size of comment content is 20")
	private String content;
	
	
}
