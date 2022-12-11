package com.sangelp.dh.domain.services.turno.impl;

import com.sangelp.dh.domain.dto.PacienteDto;
import com.sangelp.dh.domain.dto.TurnoDto;
import com.sangelp.dh.domain.dto.TurnosAsignadosDto;
import com.sangelp.dh.domain.models.Domicilio;
import com.sangelp.dh.domain.models.Odontologo;
import com.sangelp.dh.domain.models.Paciente;
import com.sangelp.dh.domain.models.Turno;
import com.sangelp.dh.domain.services.turno.TurnoService;
import com.sangelp.dh.helpers.mappers.PacienteMapper;
import com.sangelp.dh.repository.DomicilioRepository;
import com.sangelp.dh.repository.OdontologoRepository;
import com.sangelp.dh.repository.PacienteRepository;
import com.sangelp.dh.repository.TurnosRepository;
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
public class TurnoImpl implements TurnoService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private TurnosRepository turnosRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Override
    public TurnosAsignadosDto saveTurno(TurnoDto turnoDto) {
        Odontologo odontologo = odontologoRepository.findById(turnoDto.getOdontologoId()).orElse(null);
        Paciente paciente = pacienteRepository.findById(turnoDto.getPacienteId()).orElse(null);
        Turno turno = new Turno();
        if(paciente != null && odontologo != null){
            turno.setPaciente(paciente);
            turno.setOdontologo(odontologo);
        }
        return modelMapper.map(turnosRepository.save(turno),TurnosAsignadosDto.class);
    }

    @Override
    public List<TurnosAsignadosDto> findAll() {
        return turnosRepository.findAll().stream()
                .map(turno -> modelMapper.map(turno,TurnosAsignadosDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TurnosAsignadosDto findById(Long id) {
        return modelMapper.map(turnosRepository.findById(id),TurnosAsignadosDto.class);
    }

    @Override
    public boolean deleteTurno(Long id) {
        if(!turnosRepository.findById(id).isEmpty()){
            turnosRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateTurno(Map<String, Object> partialUpdate, Long id) {
        Optional<Turno> turnoFound = turnosRepository.findById(id);
        return turnoFound.map(turno -> {
            partialUpdate.forEach((key,value) -> {
                    Field field = ReflectionUtils.findField(turno.getClass(),key);
                    assert field != null;
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,turno,value);
            });
            turnosRepository.save(turno);
            return true;
        }).orElse(false);
    }

}
