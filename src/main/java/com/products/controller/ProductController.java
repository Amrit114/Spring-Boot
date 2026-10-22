package com.products.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.dto.ProductDTO;
import com.products.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(
	  name = "Product REST API CRUD operation",
	  description = "CREATE, READ, UPDATE and DELETE operation for Product REST API"		
)
@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    
	private ProductService productService;
	// getAllProducts
	@Operation(
			summary = "Fetch all Products",
			description = "REST API to fetch all products."
			   )
	@GetMapping
	public List<ProductDTO> getAllProducts() {
		return productService.getAllProducts();
	}
	// getProductById
	@Operation(
			summary = "Fetch Product by productId",
			description = "REST API to fetch Product by productId."
			   )
	@GetMapping("/{id}")
	public ProductDTO getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}
	// createProduct
	@PreAuthorize("hasAuthority('ROLE_SELLER')")
	@Operation(
			summary = "Create Products",
			description = "REST API to create products."
			   )
	@PostMapping
	public ResponseEntity<ProductDTO> crateProduct(@RequestBody ProductDTO productDTO) {
		ProductDTO createProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED );
	}
	// update Product
	@PreAuthorize("hasAuthority('ROLE_SELLER')")
	@Operation(
			summary = "Update Products By productId",
			description = "REST API to update products by productid."
			   )
	@PutMapping("/{id}")
	public ProductDTO updataProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
		return productService.updateProduct(id, productDTO);
	}
	// delete Product
	@PreAuthorize("hasAuthority('ROLE_SELLER')")
	@Operation(
			summary = "Delete Products By productId",
			description = "REST API to delete products by productid."
			   )
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable Long id) {
		return productService.deleteProduct(id);
	}
}
