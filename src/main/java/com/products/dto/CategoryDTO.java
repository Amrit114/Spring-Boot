package com.products.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
		name = "Category",
        description = "It holds category information with their products."
		)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

	private Long id;
	private String name;
	private List<ProductDTO> products;
}
