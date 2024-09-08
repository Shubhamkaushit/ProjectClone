package com.demo.productservice.services;

import java.util.List;

import com.demo.productservice.dto.GenericProductDto;
import com.demo.productservice.exceptions.ProductNotFoundException;

public interface ProductServices {

	GenericProductDto getProductById(Long id) throws ProductNotFoundException;

	List<GenericProductDto> getAllProducts();

	GenericProductDto createProduct(GenericProductDto genericProductDto);

	void updateProductById(Long id);

	GenericProductDto deleteProductById(Long id);
}
