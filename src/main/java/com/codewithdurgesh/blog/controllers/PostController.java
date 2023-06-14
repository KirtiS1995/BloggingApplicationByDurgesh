package com.codewithdurgesh.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.AppConstants;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.services.FileService;
import com.codewithdurgesh.blog.services.PostServiceI;
import com.codewithdurgesh.blog.services.UserServiceI;


@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostServiceI postServiceI;

	@Autowired
	private FileService fileService;

	@Value(("${project.image}"))
	private String path;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * @apiNote This api is for updating post
	 * @author kirti
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return 
	 */
	// post = create post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postdto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		logger.info("Request entering for create post");
		PostDto createPost = this.postServiceI.createPost(postdto, userId, categoryId);
		logger.info("Completed Request for create post");
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	/**
	 * @apiNote This api is for get post by userId
	 * @author kirti
	 * @param userId
	 * @return 
	 */
	// get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
		logger.info("Request entering for getting post with userId :{}", userId);
		List<PostDto> posts = this.postServiceI.getPostsByUser(userId);
		logger.info("Request completing for getting post with userId :{}", userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	/**
	 * @apiNote This api is for get post by categoryId
	 * @author kirti
	 * @param categoryId
	 * @return 
	 */
//	/get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		logger.info("Request entering for getting post with categoryID :{}", categoryId);
		List<PostDto> posts = this.postServiceI.getPostByCategory(categoryId);
		logger.info("Request completing for getting post with categoryID :{}", categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	/**
	 * @apiNote This api is for get single post by postId
	 * @author kirti
	 * @param postId
	 * @return 
	 */
	// Get Single Post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId) {
		logger.info("Request entering for getting post with postId :{}", postId);
		PostDto postById = postServiceI.getPostById(postId);
		logger.info("Request completing for getting post with postId :{}", postId);
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}
	/**
	 * @apiNote This api is for getting all post 
	 * @author kirti
	 * @return 
	 */
	// Get all post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(

			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		logger.info("Request entering for getting all post ");
		PostResponse postResponse = postServiceI.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		logger.info("Request completing for getting all post ");
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	/**
	 * @apiNote This api is for delete post 
	 * @author kirti
	 * @param postId
	 * @return 
	 */
	// delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable Integer postId) {
		logger.info("Request entering for getting single post with postid :{} ",postId);
		this.postServiceI.deletePost(postId);
		logger.info("Request completing for getting single post with postid :{} ",postId);
		return new ResponseEntity<String>(AppConstants.POST_DELETE, HttpStatus.OK);
	}
	/**
	 * @apiNote This api is for updating post
	 * @author kirti
	 * @param postDto
	 * @param postId
	 * @return 
	 */
	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		logger.info("Request entering for updating post with postid :{} ",postId);
		PostDto updatePost = this.postServiceI.updatePost(postDto, postId);
		logger.info("Request completing for updating post with postid :{} ",postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	/**
	 * @apiNote This api for searching post by title
	 * @author kirti
	 * @param keywords
	 * @return
	 */
	//Search Post
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable("keywords") String keywords){
		logger.info("Request entering for searching post with keyword :{}",keywords);
		List<PostDto> searchPosts = this.postServiceI.searchPosts(keywords);
		logger.info("Request completing for searching post with keyword :{}",keywords);
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.FOUND);
	}
	
	// Post image upload

	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestPart("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		logger.info("Request entering for uploading image  ");
		PostDto postDto = this.postServiceI.getPostById(postId);
		String filename = this.fileService.uploadImage(path, image);
		postDto.setImage(filename);
		PostDto updatePost = this.postServiceI.updatePost(postDto, postId);
		logger.info("Request completed for uploading image  ");
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	//method to serve files
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
	
}
