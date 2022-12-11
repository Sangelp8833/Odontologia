package com.sangelp.dh.domain.services.paciente.impl;

import com.sangelp.dh.domain.dto.PacienteDto;
import com.sangelp.dh.domain.models.Domicilio;
import com.sangelp.dh.domain.models.Paciente;
import com.sangelp.dh.domain.models.Turno;
import com.sangelp.dh.domain.services.odontologo.impl.OdontologoImpl;
import com.sangelp.dh.domain.services.paciente.PacienteService;
import com.sangelp.dh.helpers.mappers.PacienteMapper;
import com.sangelp.dh.repository.DomicilioRepository;
import com.sangelp.dh.repository.PacienteRepository;
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
public class PacienteImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private TurnosRepository turnosRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PacienteMapper pacienteMapper;

    final Logger LOGGER = Logger.getLogger(PacienteImpl.class);

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
        List<Turno> turnos = turnosRepository.findByOdontologoOrPaciente(null,pacienteRepository.findById(id).get());
        if(!pacienteRepository.findById(id).isEmpty() && turnos.isEmpty()){
            pacienteRepository.deleteById(id);
            LOGGER.info("Se ha eliminado correctamente el paciente.");
            return true;
        }else{
            LOGGER.error("Verifique si el paciente existe, en caso tal que si exista verifique que no tenga turnos asignados.");
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
                    }else if(value instanceof Long){
                        paciente.setDni((Long) value);
                    }else{
                        throw new RuntimeException("El tipo del valor ingresado no es válido o no es numérico.");
                    }
                }else{
                    Field field = ReflectionUtils.findField(paciente.getClass(),key);
                    assert field != null;
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,paciente,value);
                }
            });
            pacienteRepository.save(paciente);
            LOGGER.info("Se ha actualizado correctamente el paciente.");
            return true;
        }).orElse(false);
    }
}
