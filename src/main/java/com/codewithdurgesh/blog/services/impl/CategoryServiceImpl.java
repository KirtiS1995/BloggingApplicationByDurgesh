package com.codewithdurgesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.AppConstants;
import com.codewithdurgesh.blog.payloads.CategoryDto;
import com.codewithdurgesh.blog.repositories.CategoryRepo;
import com.codewithdurgesh.blog.services.CategoryServiceI;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryServiceI{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
/**
 * @implNote This method is for creating Category
 * @author kirti
 * @param categoryDto
 * @return 
 */
	@Override
	public CategoryDto createCategory(CategoryDto cat) {
		log.info("Entering DAO call for creating Category ");
		Category category = this.modelmapper.map(cat, Category.class);
		Category category2 = this.categoryRepo.save(category);		
		CategoryDto categoryDto = this.modelmapper.map(category2, CategoryDto.class);
		log.info("Completing DAO call for creating category ");
		return categoryDto;
	}
	
	/**
	 * @implNote This method is for updating Category
	 * @author kirti
	 * @param categoryDto
	 * @param catgoryID
	 * @return 
	 */
	@Override
	public CategoryDto updateCategory(CategoryDto cat, Integer categoryId) {
		log.info("Entering DAO call for updating Category with categoryID : {} ",categoryId);
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
		category.setCategoryTitle(cat.getCategoryTitle());
		category.setCategoryDescription(cat.getCategoryDescription());
		Category category2 = this.categoryRepo.save(category);
		log.info("Completing DAO call for updating Category with categoryID : {} ",categoryId);
		return this.modelmapper.map(category2, CategoryDto.class);
	}
	
	/**
	 * @implNote This method is for get single Category
	 * @author kirti
	 * @param categoryId
	 * @return 
	 */
	@Override
	public CategoryDto getSingleCategory(Integer categoryId) {
		log.info("Entering DAO call for getting single Category with categoryID : {} ",categoryId);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
		log.info("Completing DAO call for getting single Category with categoryID : {} ",categoryId);
		return this.modelmapper.map(category, CategoryDto.class);
	}

	/**
	 * @implNote This method is for getting all Category
	 * @author kirti
	 * @return 
	 */
	@Override
	public List<CategoryDto> getAllCategory() {
		log.info("Entering DAO call for getting all Category ");
			List<Category> list = this.categoryRepo.findAll();	
			List<CategoryDto> categories = list.stream().map((cat) -> this.modelmapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
			log.info("Completing DAO call for gtting all category ");
		return categories;
	}

	/**
	 * @implNote This method is for deleting Category
	 * @author kirti
	 * @param categoryId
	 * @return 
	 */
	@Override
	public void deleteCategory(Integer categoryId) {
		log.info("Entering DAO call for deleting Category  with categoryID :{}",categoryId);
		Category category = this.categoryRepo.findById(categoryId)
		.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
		log.info("Completing DAO call for deleting Category  with categoryID :{}",categoryId);
		this.categoryRepo.delete(category);
	}
	

}
