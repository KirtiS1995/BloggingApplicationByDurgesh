package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;

public interface PostServiceI {

	//create	
	PostDto createPost(PostDto postdto,Integer userId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postdto,Integer postId);

	//delete
	void deletePost(Integer postId);
	
	//getsingle Post
	PostDto getPostById(Integer postId);
	
	//getall post 	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
		
	////get all post by category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//get all post by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//Search posts
	List<PostDto> searchPosts(String keyword);

		


}
