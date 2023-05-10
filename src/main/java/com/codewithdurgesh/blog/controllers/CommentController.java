package com.codewithdurgesh.blog.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.AppConstants;
import com.codewithdurgesh.blog.payloads.CommentDto;
import com.codewithdurgesh.blog.services.CommentServiceI;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentServiceI commentServiceI;
	
	Logger logger=LoggerFactory.getLogger(CommentController.class);
	
	/**
	 * @apiNote This method is for creating Comment
	 * @author kirti
	 * @param commentDto
	 * @param postId
	 * @return 
	 */
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment( @RequestBody CommentDto commentDto,@PathVariable Integer postId) {
		logger.info("Request entering for creating comment ");
		CommentDto comment = this.commentServiceI.createComment(commentDto, postId);
		logger.info("Request completed for creating comment ");
		return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
	}

	/**
	 * @apiNote This method is for deleting Comment
	 * @author kirti
	 * @param commentId
	 * @return 
	 */
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<String> deleteComment( @PathVariable Integer commentId) {
		logger.info("Request entering for deleting comment  with commentId : {}", commentId);
		this.commentServiceI.deleteComment(commentId);
		logger.info("Request completed for deleting comment with commentId : {}", commentId);
		return new ResponseEntity<String>(AppConstants.COMMENT_DELETE, HttpStatus.OK);
	}
	
}
