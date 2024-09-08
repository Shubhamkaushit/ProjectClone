package com.demo.productservice.dto;

import com.demo.productservice.models.BaseModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto extends BaseModel{

	private String title;
	private String description;
	private String category;
	private int price;
	private String image;
}
