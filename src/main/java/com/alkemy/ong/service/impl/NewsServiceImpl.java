package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.BadRequestException;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.mappers.ModelMapperFacade;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.INewsRepository;
import com.alkemy.ong.service.INewsService;
import com.alkemy.ong.util.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsServiceImpl implements INewsService {

    @Autowired
    INewsRepository newsRepository;
    @Autowired
    MessageHandler messageHandler;

    @Override
    public NewsDto getNewsById(Long id) throws ResourceNotFoundException{
        Optional<News> news =newsRepository.findById(id);
        if (!news.isPresent()){
            throw new ResourceNotFoundException(messageHandler.newsNotFound);
        }
        return ModelMapperFacade.map(news,NewsDto.class);
    }
}