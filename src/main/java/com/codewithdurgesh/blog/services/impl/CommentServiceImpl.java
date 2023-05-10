package com.codewithdurgesh.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Comment;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.CommentDto;
import com.codewithdurgesh.blog.repositories.CommentRepo;
import com.codewithdurgesh.blog.repositories.PostRepo;
import com.codewithdurgesh.blog.services.CommentServiceI;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentServiceImpl implements CommentServiceI{

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * @implNote This method is for creating Comment
	 * @author kirti
	 * @param commentDto
	 * @param postId
	 * @return 
	 */
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		log.info("Entering DAO call for creating Comment with postId : {}",postId);
		Post post = this.postRepo.findById(postId).orElseThrow( () -> new ResourceNotFoundException("Post", "postID", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment comment2 = this.commentRepo.save(comment);
		log.info("Completed DAO call for creating Comment with postId : {}",postId);
		return this.modelMapper.map(comment2, CommentDto.class);
	}

	/**
	 * @implNote This method is for deleting Comment
	 * @author kirti
	 * @param commentId
	 * @return 
	 */
	@Override
	public void deleteComment(Integer commentId) {
		log.info("Entering DAO call for deleting Comment with commentId : {}",commentId);
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentID", commentId));
		this.commentRepo.delete(comment);
		log.info("Completing DAO call for deleting Comment with postId : {}",commentId);
	}

}
