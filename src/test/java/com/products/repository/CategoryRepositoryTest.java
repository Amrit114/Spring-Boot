package com.products.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.products.entity.Category;

@DataJpaTest
class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	private Category category;
	
	@BeforeEach
	void setUp() throws Exception {
		//insert row in category table
		category = new Category();
		category.setName("test");
		categoryRepository.save(category);
	}

	@AfterEach
	void tearDown() throws Exception {
		categoryRepository.delete(category);
	}

	@Test
	void FindByName() {
		Category foundCategory = categoryRepository.findByName("test").orElse(null);
		assertNotNull(foundCategory);
		assertEquals(category.getName(), foundCategory.getName());
	}

	@Test
	void DeleteByName() {
		categoryRepository.deleteByName("test");
		Category foundCategory = categoryRepository.findByName("test").orElse(null);
		assertNull(foundCategory);
	}

}
