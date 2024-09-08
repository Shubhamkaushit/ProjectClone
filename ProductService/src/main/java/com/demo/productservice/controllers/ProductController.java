package com.demo.productservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.productservice.dto.ExceptionDto;
import com.demo.productservice.dto.GenericProductDto;
import com.demo.productservice.exceptions.ProductNotFoundException;
import com.demo.productservice.services.ProductServices;

@RestController
@RequestMapping("/products")
public class ProductController {

	private ProductServices productServices;

	// Constructor Injection
	public ProductController(@Qualifier("fakeStoreProductService") ProductServices productServices) {
		// TODO Auto-generated constructor stub

		this.productServices = productServices;
	}

	@GetMapping("/{id}")
	public GenericProductDto getProductById(@PathVariable Long id) throws ProductNotFoundException {

		return productServices.getProductById(id);
	}

	@GetMapping("")
	public List<GenericProductDto> getAllProducts() {

		return productServices.getAllProducts();
	}

	@PostMapping("")
	public GenericProductDto createProduct(@RequestBody GenericProductDto genericProductDto) {

		return productServices.createProduct(genericProductDto);
	}

	@PutMapping("/{id}")
	public void updateProductById(@PathVariable Long id) {

	}

	@DeleteMapping("/{id}")
	public GenericProductDto deleteProductById(@PathVariable Long id) {

		return productServices.deleteProductById(id);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	private ResponseEntity<ExceptionDto> productNotFoundExceptionHandler(ProductNotFoundException productNotFoundException){
		
		ExceptionDto exceptionDto = new ExceptionDto();
		
		exceptionDto.setHttpStatus(HttpStatus.NOT_FOUND);
		exceptionDto.setMessage(productNotFoundException.getMessage());
		
		ResponseEntity responseEntity = 
				new ResponseEntity(exceptionDto, HttpStatus.NOT_FOUND);
		
		return responseEntity;
	}

}
