package com.sangelp.dh.domain.services.odontologo.impl;

import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.models.Odontologo;
import com.sangelp.dh.domain.models.Turno;
import com.sangelp.dh.domain.services.odontologo.OdontologoService;
import com.sangelp.dh.repository.OdontologoRepository;
import com.sangelp.dh.repository.TurnosRepository;
import org.apache.log4j.Logger;
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
    private TurnosRepository turnosRepository;

    @Autowired
    private ModelMapper modelMapper;

    final Logger LOGGER = Logger.getLogger(OdontologoImpl.class);

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
    public OdontologoDto findByMatricula(Long matricula) {
        if(odontologoRepository.findByMatricula(matricula) == null){
            LOGGER.info("No se ha encontrado ningún odontólogo con la matrícula especificada.");
            return null;
        }else  {
            return modelMapper.map(odontologoRepository.findByMatricula(matricula),OdontologoDto.class) ;
        }
    }

    @Override
    public boolean deleteOndontologo(Long id) {
        List<Turno> turnos = turnosRepository.findByOdontologoOrPaciente(odontologoRepository.findById(id).get(),null);
        if(!odontologoRepository.findById(id).isEmpty() && turnos.isEmpty()){
            odontologoRepository.deleteById(id);
            LOGGER.info("Se ha eliminado correctamente el odontólogo.");
            return true;
        }else{
            LOGGER.error("Verifique si el odontólogo existe, en caso tal que si exista verifique que no tenga turnos asignados.");
            return false;
        }
    }

    @Override
    public boolean updateOdontologo(Map<String, Object> partialUpdate, Long id) {
        Optional<Odontologo> odontologoFound = odontologoRepository.findById(id);
        return odontologoFound.map(odontologo -> {
            partialUpdate.forEach((key,value) -> {
                if(key.equals("matricula")){
                    if(value instanceof Integer){
                        Long newValue =  Long.valueOf((Integer) value);
                        odontologo.setMatricula(newValue);
                    }else{
                        odontologo.setMatricula((Long) value);
                    }
                }else{
                    Field field = ReflectionUtils.findField(odontologo.getClass(),key);
                    assert field != null;
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,odontologo,value);
                }
            });
            odontologoRepository.save(odontologo);
            LOGGER.info("Se ha actualizado correctamente el odontólogo.");
            return true;
        }).orElse(false);
    }


}
