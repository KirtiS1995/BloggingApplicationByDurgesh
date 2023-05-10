package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.payloads.CategoryDto;

public interface CategoryServiceI {

	//create category
	CategoryDto createCategory(CategoryDto cat);
	
	//update Category
	CategoryDto updateCategory(CategoryDto cat,Integer categoryId);
	
	//getsingle Category
	CategoryDto getSingleCategory(Integer categoryId);
	
	//Get All Category
	List<CategoryDto> getAllCategory();
	
	//delete Category
	void deleteCategory(Integer categoryId);

	
	
	
}
