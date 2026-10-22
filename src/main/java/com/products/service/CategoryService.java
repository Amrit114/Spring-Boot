package com.products.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.products.dto.CategoryDTO;
import com.products.entity.Category;
import com.products.exception.CategoryAlreadyExistsException;
import com.products.mapper.CategoryMapper;
import com.products.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

	private CategoryRepository categoryRepository;
	// create category
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		
		Optional<Category> optionalCategory = categoryRepository.findByName(categoryDTO.getName());
		if(optionalCategory.isPresent()) {
			throw new CategoryAlreadyExistsException("Category "+ categoryDTO.getName() +" already exist!");
		}
		Category category = CategoryMapper.toCategoryEntity(categoryDTO);
		category = categoryRepository.save(category);
		return CategoryMapper.toCategoryDTO(category);
	}
	// get all categories
	public List<CategoryDTO> getAllCategories(){
		return categoryRepository.findAll().stream().map(CategoryMapper::toCategoryDTO).toList();
	}
	// get category by id
	public CategoryDTO getCategoryById(Long id) {
	  Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
	  return CategoryMapper.toCategoryDTO(category);
	}
	// delete category
	public String deleteCategory(Long id) {
		categoryRepository.deleteById(id);
		return "Category " + id + " has been deleted successfully";
	}
	
}
