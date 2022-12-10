package com.sangelp.dh.domain.services.paciente.impl;

import com.sangelp.dh.domain.dto.PacienteDto;
import com.sangelp.dh.domain.models.Domicilio;
import com.sangelp.dh.domain.models.Paciente;
import com.sangelp.dh.domain.services.paciente.PacienteService;
import com.sangelp.dh.helpers.mappers.PacienteMapper;
import com.sangelp.dh.repository.DomicilioRepository;
import com.sangelp.dh.repository.PacienteRepository;
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
public class PacienteImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Override
    public PacienteDto savePaciente(PacienteDto pacienteDto) {
        Paciente paciente = pacienteMapper.dto2Model(pacienteDto);
        return modelMapper.map(pacienteRepository.save(paciente),PacienteDto.class);
    }

    @Override
    public List<PacienteDto> findAll() {
        return pacienteRepository.findAll().stream()
                .map(paciente -> modelMapper.map(paciente,PacienteDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PacienteDto findById(Long id) {
        return modelMapper.map(pacienteRepository.findById(id),PacienteDto.class);
    }

    @Override
    public PacienteDto findByDni(Long dni) {
        if(pacienteRepository.findByDni(dni) == null){
            return null;
        }else  {
            return modelMapper.map(pacienteRepository.findByDni(dni),PacienteDto.class) ;
        }
    }

    @Override
    public boolean deletePaciente(Long id) {
        if(!pacienteRepository.findById(id).isEmpty()){
            pacienteRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updatePaciente(Map<String, Object> partialUpdate, Long id) {
        Optional<Paciente> pacienteFound = pacienteRepository.findById(id);
        return pacienteFound.map(paciente -> {
            partialUpdate.forEach((key,value) -> {
                if(key.equals("domicilio")){
                    Domicilio domicilio = modelMapper.map(value,Domicilio.class);
                    Domicilio domicilioSaved = domicilioRepository.save(domicilio);
                    paciente.setDomicilio(domicilioSaved);
                }else if(key.equals("dni")){
                    if(value instanceof Integer){
                        Long newValue =  Long.valueOf((Integer) value);
                        paciente.setDni(newValue);
                    }else{
                        paciente.setDni((Long) value);
                    }
                }else{
                    Field field = ReflectionUtils.findField(paciente.getClass(),key);
                    assert field != null;
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,paciente,value);
                }
            });
            pacienteRepository.save(paciente);
            return true;
        }).orElse(false);
    }
}
