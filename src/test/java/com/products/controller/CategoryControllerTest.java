package com.products.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.products.dto.CategoryDTO;
import com.products.service.CategoryService;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
	
	@InjectMocks
	private CategoryController categoryController;
	
	@Mock
	private CategoryService categoryService;
	
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
    void test_getAllCategories() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("test");
        List<CategoryDTO> categoryList = List.of(categoryDTO);
        when(categoryService.getAllCategories()).thenReturn(categoryList);
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.size()").value(categoryList.size()))
                .andExpect(jsonPath("$[0].name").value(categoryDTO.getName()));

    }

    @Test
    public void test_createCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("test");
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(categoryDTO);
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"test\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(categoryDTO.getName()));
    }

}
