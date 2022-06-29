package com.alkemy.ong.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {

	private Long id;

	@NotNull(message = "Name must not be null.")
	private String name;

	private String description;
	private String image;

}