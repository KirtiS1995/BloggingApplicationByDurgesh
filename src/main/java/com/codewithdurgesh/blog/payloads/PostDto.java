package com.codewithdurgesh.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Comment;
import com.codewithdurgesh.blog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private Integer postId;
	
	@NotEmpty
	@Size(min = 4,max = 10,message = "Post title must be min 4 characters and max 10 characters")
	private String title;
	
	@NotEmpty
	@Size(min = 4,max = 30,message = "Post content must be min 4 characters and max 30 characters")
	private String content;

	private String image;
	
	private Date addedDate;
	
	private UserDto user;
	
	private CategoryDto category;
	
	private Set<CommentDto> comments=new HashSet<>();


	
}
