package com.codewithdurgesh.blog.services;

import com.codewithdurgesh.blog.payloads.CommentDto;

public interface CommentServiceI {

	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	void deleteComment(Integer commentId);
}
