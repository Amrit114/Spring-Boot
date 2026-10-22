package com.products.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.products.dto.ProductDTO;
import com.products.entity.Category;
import com.products.entity.Product;
import com.products.exception.CategoryNotFoundException;
import com.products.mapper.ProductMapper;
import com.products.repository.CategoryRepository;
import com.products.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;
	
	//create product
	public ProductDTO createProduct(ProductDTO productDTO) {
		/**
		 * * name,description,price,categoryId
		 */
	     Category category = categoryRepository.findById(productDTO.getCategoryId())
	     .orElseThrow(()-> new CategoryNotFoundException("Category id " 
	                           + productDTO.getCategoryId() +" not found!"));
	   
	     // DTO -> entity
	     Product product = ProductMapper.toProductEntity(productDTO, category);
	     product = productRepository.save(product);
	     // Entity -> DTO
	    
	     return ProductMapper.toProductDTO(product);
	     
	}
	
	// Get All Products
	public List<ProductDTO> getAllProducts(){
		return productRepository.findAll().stream().map(ProductMapper::toProductDTO).toList();
	}
	
	// Get Product By Id
	public ProductDTO getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product Not Found!"));
	    return ProductMapper.toProductDTO(product);
	}
	
	//update Product
	public ProductDTO updateProduct(Long id,ProductDTO productDTO) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product Not Found!"));
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category Not Found!"));
		
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setCategory(category);
		productRepository.save(product);
		
		return ProductMapper.toProductDTO(product);
	}
	
	// Delete Product
	public String deleteProduct(Long id) {
		productRepository.deleteById(id);
		return "Product "+id+" has been deleted successfully"; 
	}
	
}
