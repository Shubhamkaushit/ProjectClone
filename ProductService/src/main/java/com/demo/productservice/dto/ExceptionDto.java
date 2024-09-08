package com.demo.productservice.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDto {

	private HttpStatus httpStatus;
	private String message;
}
