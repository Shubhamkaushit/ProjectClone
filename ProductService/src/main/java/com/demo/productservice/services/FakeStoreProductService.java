package com.demo.productservice.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.demo.productservice.dto.FakeStoreProductDto;
import com.demo.productservice.dto.GenericProductDto;
import com.demo.productservice.exceptions.ProductNotFoundException;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductServices {
	
	private RestTemplateBuilder restTemplateBuilder;
	
	private String idSpecificUrl = "https://fakestoreapi.com/products/{id}";
	
	private String genericProductUrl = "https://fakestoreapi.com/products"; 
	
	FakeStoreProductService(RestTemplateBuilder restTemplateBuilder){
		
		this.restTemplateBuilder = restTemplateBuilder;
	}

	@Override
	public GenericProductDto getProductById(Long id) throws ProductNotFoundException{
		// TODO Auto-generated method stub
		
		RestTemplate restTemplate = restTemplateBuilder.build();
		ResponseEntity<FakeStoreProductDto> responseEntity = 
				restTemplate.getForEntity(idSpecificUrl, FakeStoreProductDto.class, id);
		
		if(responseEntity.getBody() == null) {
			
			throw new ProductNotFoundException("The product with id: "+id+" was not found");
		}
		
		return convertToGenericProductDto(responseEntity.getBody());
	}

	@Override
	public List<GenericProductDto> getAllProducts() {
		// TODO Auto-generated method stub
		
		RestTemplate restTemplate = restTemplateBuilder.build();
		ResponseEntity<FakeStoreProductDto[]> responseEntity = 
				restTemplate.getForEntity(genericProductUrl, FakeStoreProductDto[].class);
		
		List<GenericProductDto> result = new ArrayList<GenericProductDto>();
		List<FakeStoreProductDto> fakeStoreProductDtos = Arrays.asList(responseEntity.getBody());
		
		for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductDtos) {
			
			result.add(convertToGenericProductDto(fakeStoreProductDto));
		}
		
		return result;
	}

	@Override
	public GenericProductDto createProduct(GenericProductDto genericProductDto) {
		// TODO Auto-generated method stub

		RestTemplate restTemplate = restTemplateBuilder.build();
		ResponseEntity<FakeStoreProductDto> responseEntity = 
				restTemplate.postForEntity(genericProductUrl, genericProductDto, FakeStoreProductDto.class);
		
		return convertToGenericProductDto(responseEntity.getBody());
	}

	@Override
	public void updateProductById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public GenericProductDto deleteProductById(Long id) {
		// TODO Auto-generated method stub

		RestTemplate restTemplate = restTemplateBuilder.build();
		//ResponseEntity<FakeStoreProductDto> responseEntity = 
				//restTemplate.getForEntity(null, null);
		
		RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
		ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = 
				restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
		
		ResponseEntity<FakeStoreProductDto> responseEntity = 
				restTemplate.execute(idSpecificUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id);
		
		return convertToGenericProductDto(responseEntity.getBody());
	}

	private GenericProductDto convertToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {

		GenericProductDto genericProductDto = new GenericProductDto();

		genericProductDto.setId(fakeStoreProductDto.getId());
		genericProductDto.setTitle(fakeStoreProductDto.getTitle());
		genericProductDto.setPrice(fakeStoreProductDto.getPrice());
		genericProductDto.setDescription(fakeStoreProductDto.getDescription());
		genericProductDto.setCategory(fakeStoreProductDto.getCategory());
		genericProductDto.setImage(fakeStoreProductDto.getImage());

		return genericProductDto;
	}

}
