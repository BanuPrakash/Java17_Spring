package com.adobe.orderapp;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.adobe.orderapp.api.ProductController;
import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.OrderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	@MockBean
	private OrderService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getProductsTest() throws Exception {
		List<Product> products = new ArrayList<>();
		products.add(Product.builder().id(1).name("A").price(100.00).build());
		products.add(Product.builder().id(2).name("B").price(200.00).build());
		
		when(service.getProducts()).thenReturn(products); // mocking
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("A")));

		verify(service, times(1)).getProducts();
	}
	
	
	
	@Test
	public void addProductTest() throws Exception {
		Product p = Product.builder().name("test").price(1500.00).quantity(100).build();
		ObjectMapper mapper = new ObjectMapper(); // JAva  <--> JSON
		String json = mapper.writeValueAsString(p); 
//		
//		json = """
//				{
//					name:"A",
//					price:45454
//				}
//				""";
		when(service.addProduct(Mockito.any(Product.class)))
			.thenReturn(p); // mocking
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
		 .content(json)
		 .contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("test") ))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		verify(service, times(1)).addProduct(Mockito.any(Product.class));
	}

	@Test
	public void addProductExceptionTest() throws Exception {
		Product p = Product.builder().name("").price(0.00).quantity(-80).build();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p); 
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
		 .content(json)
		 .contentType(MediaType.APPLICATION_JSON))
		 .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", Matchers.hasSize(3)))
		 .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", Matchers.hasItem("Name is required")));
		
		verifyNoInteractions(service);
		
	}
}
