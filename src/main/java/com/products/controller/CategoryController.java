package com.products.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.dto.CategoryDTO;
import com.products.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(
		  name = "Category REST API CRUD operation",
		  description = "CREATE, READ and DELETE operation for Category REST API"		
	)
@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

	private CategoryService categoryService;
	// get all Categories
	@Operation(
			summary = "Fetch all Category",
			description = "REST API to fetch all category along with their products."
			   )
	@GetMapping
	public List<CategoryDTO> getAllCategories(){
		return categoryService.getAllCategories();
	}
	// create Categories
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Operation(
			summary = "Create Category",
			description = "REST API to create category."
			   )
	@ApiResponse(
			responseCode = "201",
			description = "CREATED"
			)
	@PostMapping
	public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
		
			CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
		
	}
	// get Categories by id
	@Operation(
			summary = "Fetch Category By categoryId",
			description = "REST API to fetch category by categoryid along with their products."
			   )
	@GetMapping("/{id}")
	public CategoryDTO getCategoryById(@PathVariable Long id) {
		return categoryService.getCategoryById(id);
		
	}
	// delete category
	@Operation(
			summary = "Delete Category By categoryId",
			description = "REST API to Update category by categoryid along with their products."
			   )
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public String deleteCategory(@PathVariable Long id) {
		return categoryService.deleteCategory(id);
	}
	
}
