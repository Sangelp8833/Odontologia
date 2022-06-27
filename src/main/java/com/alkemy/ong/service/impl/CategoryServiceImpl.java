package com.alkemy.ong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.BadRequestException;
import com.alkemy.ong.mappers.ModelMapperFacade;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.ICategoryRepository;
import com.alkemy.ong.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {
	
	@Autowired
	private ICategoryRepository categoryRepository;
	
	@Override
	public CategoryDto save(CategoryDto categoryDto) {
        if (!categoryDto.getName().matches("^[a-zA-Z]*$")) throw new BadRequestException("The name only supports alphabetic characters.");
		return ModelMapperFacade.map(
				categoryRepository.save(ModelMapperFacade.map(
						categoryDto, Category.class)),
				CategoryDto.class);
	}

}
