package com.sangelp.dh.domain.services.paciente.impl;

import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.dto.PacienteDto;
import com.sangelp.dh.domain.models.Odontologo;
import com.sangelp.dh.domain.models.Paciente;
import com.sangelp.dh.domain.services.paciente.PacienteService;
import com.sangelp.dh.repository.OdontologoRepository;
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
    private ModelMapper modelMapper;

    @Override
    public PacienteDto savePaciente(PacienteDto pacienteDto) {
        Paciente paciente = modelMapper.map(pacienteDto,Paciente.class);
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
    public PacienteDto findByNombre(String nombre) {
        if(pacienteRepository.findByNombre(nombre) == null){
            return null;
        }else  {
            return modelMapper.map(pacienteRepository.findByNombre(nombre),PacienteDto.class) ;
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

                    Field field = ReflectionUtils.findField(paciente.getClass(),key);
                    assert field != null;
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,paciente,value);

            });
            pacienteRepository.save(paciente);
            return true;
        }).orElse(false);
    }


}
