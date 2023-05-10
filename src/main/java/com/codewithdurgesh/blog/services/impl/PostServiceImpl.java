package com.codewithdurgesh.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.AppConstants;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.repositories.CategoryRepo;
import com.codewithdurgesh.blog.repositories.PostRepo;
import com.codewithdurgesh.blog.repositories.UserRepo;
import com.codewithdurgesh.blog.services.PostServiceI;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostServiceI {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	/**
	 * @implNote This method is for creating post
	 * @author kirti
	 * @param postDto
	 * @param categoryId
	 * @param userId
	 * @return 
	 */
	@Override
	public PostDto createPost(PostDto postdto, Integer userId, Integer categoryId) {
		log.info("Entering DAO call for creating Post with userId and CategoryId :{}",userId,categoryId);
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","userId", userId));
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","categoryId",categoryId));
		Post post = this.modelMapper.map(postdto, Post.class);
		post.setImage("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post post2 = this.postRepo.save(post);
		log.info("Completed DAO call for creating post with userId and CategoryId :{}",userId,categoryId);
		return this.modelMapper.map(post2, PostDto.class);
	}
	
	/**
	 * @implNote This method is for updating post
	 * @author kirti
	 * @param postDto
	 * @param postId
	 * @return 
	 */
	@Override
	public PostDto updatePost(PostDto postdto, Integer postId) {
		log.info("Entering DAO call for updating post with postID: {} ", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","PostId",postId));
		post.setTitle(postdto.getTitle());
		post.setContent(postdto.getContent());
		post.setImage(postdto.getImage());
		Post post2 = this.postRepo.save(post);
		log.info("Completed DAO call for updating post with postID: {} ", postId);
		return this.modelMapper.map(post2, PostDto.class);
	}

	/**
	 * @implNote This method is for deleting post
	 * @author kirti
	 * @param postId
	 * @return 
	 */
	@Override
	public void deletePost(Integer postId) {
		log.info("Entering DAO call for deleting  Post with postId :{} ",postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","postId",postId));
		log.info("Completing DAO call for deleting  Post with postId :{} ",postId);
		this.postRepo.delete(post);
	}
	/**
	 * @implNote This method is for get post by id
	 * @author kirti
	 * @param postId
	 * @return 
	 */
	@Override
	public PostDto getPostById(Integer postId) {
		log.info("Entering DAO call for Getting single Post with postId :{} ", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","postId", postId));
		log.info("Completed DAO call for Getting single Post with postId :{} ", postId);
		return this.modelMapper.map(post, PostDto.class);
	}

//	@Override
//	public List<PostDto> getAllPost() {
//		List<Post> allPosts = this.postRepo.findAll();
//		List<PostDto> postdtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
//				.collect(Collectors.toList());
//		return postdtos;
//	}
	/**
	 * @implNote This method is for getting all post
	 * @author kirti
	 * @return 
	 */
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		log.info("Entering DAO call for getting all Post ");
		// Using ternary operator
		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

//		if (sortDir.equalsIgnoreCase("asc")) {
//			sort=Sort.by(sortBy).ascending();}
//		else {
//			sort=Sort.by(sortBy).descending();		}

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = this.postRepo.findAll(p);

		List<Post> allPosts = pagePost.getContent(); // for getting list of pAGE

		List<PostDto> postdtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postdtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		log.info("Completed DAO call for getting all Post");

		return postResponse;
	}
	/**
	 * @implNote This method is for getting post by category
	 * @author kirti
	 * @param categoryId
	 * @return 
	 */
	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		log.info("Entering DAO call for getting Post by category with categoryId : {}", categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","categoryId",categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> postdtoList = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed DAO call for getting Post by category with categoryId : {}", categoryId);
		return postdtoList;
	}
	
	/**
	 * @implNote This method is for getting post by user id
	 * @author kirti
	 * @param userId
	 * @return 
	 */
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		log.info("Entering DAO call for getting Post by User with userId : {}",userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","userId", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postdtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed DAO call for getting Post by User with userId : {}",userId);
		return postdtos;
	}
	/**
	 * @implNote This method is for searching post by title
	 * @author kirti
	 * @param keyword
	 * @return 
	 */
	@Override
	public List<PostDto> searchPosts(String keyword) {
		log.info("Entering DAO call for searching Post by title {}",keyword);
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postdtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed DAO call for getting Post by title {} ",keyword);
		return postdtos;
	}

}
