package com.codewithdurgesh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.AppConstants;
import com.codewithdurgesh.blog.payloads.CategoryDto;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.services.CategoryServiceI;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryServiceI categoryServiceI;
	
	Logger logger=LoggerFactory.getLogger(CategoryController.class);

	/**
	 * @apiNote This api is for creating category
	 * @author kirti
	 * @param catDto
	 * @return 
	 */
	//post = create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catDto) {
		logger.info("Request entering for create category");
		CategoryDto category = this.categoryServiceI.createCategory(catDto);
		logger.info("Request completed for create category");
		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}

	/**
	 * @apiNote This api is for updating category
	 * @author kirti
	 * @param catDto
	 * @param categoryId
	 * @return 
	 */
	// put= update category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updatecategory(@Valid @RequestBody CategoryDto catDto,
			@PathVariable Integer categoryId) {
		logger.info("Request entering for updating category with categoryId :{}", categoryId);
		CategoryDto updatedcategory = this.categoryServiceI.updateCategory(catDto, categoryId);
		logger.info("Request completed for updating category with categoryId :{}", categoryId);
		return ResponseEntity.ok(updatedcategory);
	}	
	/**
	 * @apiNote This api is for deleting category
	 * @author kirti
	 * @param categoryId
	 * @return 
	 */
		//delete = delete category
		@DeleteMapping("/{categoryId}")
		public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
			logger.info("Request entering for deleting category with categoryId :{}", categoryId);
			this.categoryServiceI.deleteCategory(categoryId);
			logger.info("Request completed for deleting category with categoryId :{}", categoryId);
			return new ResponseEntity<String>(AppConstants.CATEGORY_DELETE, HttpStatus.OK);
		}
		
		/**
		 * @apiNote This api is for getting all category
		 * @author kirti
		 * @return 
		 */
		// get = get all category
		@GetMapping("/")
		public ResponseEntity<List<CategoryDto>> getAllCategories() {
			logger.info("Request entering for getting all category ");
			List<CategoryDto> allCategory = this.categoryServiceI.getAllCategory();
			logger.info("Request completed for getting all category" );
			return new ResponseEntity<List<CategoryDto>>(allCategory,HttpStatus.OK);
		}
		/**
		 * @apiNote This api is for getting single category
		 * @author kirti
		 * @param categoryId
		 * @return 
		 */
		// get = get single category
		@GetMapping("/{categoryId}")
		public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId) {
			logger.info("Request entering for getting single category with categoryId :{}", categoryId);
			CategoryDto singleCategory = this.categoryServiceI.getSingleCategory(categoryId);
			logger.info("Request completed for getting single category with categoryId :{}", categoryId);
			return new ResponseEntity<CategoryDto>(singleCategory, HttpStatus.OK);
		}

}
