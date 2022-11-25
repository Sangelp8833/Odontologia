package com.sangelp.dh.domain.services.odontologo.impl;

import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.models.Odontologo;
import com.sangelp.dh.domain.services.odontologo.OdontologoService;
import com.sangelp.dh.repository.OdontologoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OdontologoImpl implements OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OdontologoDto saveOdontologo(OdontologoDto odontologoDto) {
        Odontologo odontologo = modelMapper.map(odontologoDto,Odontologo.class);
        return modelMapper.map(odontologoRepository.save(odontologo),OdontologoDto.class);
    }

    @Override
    public List<OdontologoDto> findAll() {
        return odontologoRepository.findAll().stream()
                .map(odontologo -> modelMapper.map(odontologo,OdontologoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OdontologoDto findById(Long id) {
        return modelMapper.map(odontologoRepository.findById(id),OdontologoDto.class);
    }

    @Override
    public boolean deleteOndontologo(Long id) {
        if(!odontologoRepository.findById(id).isEmpty()){
            odontologoRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateOdontologo(Map<String, Object> partialUpdate, Long id) {
        Optional<Odontologo> odontologoFound = odontologoRepository.findById(id);
        return odontologoFound.map(odontologo -> {
            partialUpdate.forEach((key,value) -> {
                Field field = ReflectionUtils.findField(odontologo.getClass(),key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field,odontologo,value);
            });
            odontologoRepository.save(odontologo);
            return true;
        }).orElse(false);
    }
}
