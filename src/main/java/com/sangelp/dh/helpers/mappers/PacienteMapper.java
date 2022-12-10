package com.sangelp.dh.helpers.mappers;

import com.sangelp.dh.domain.dto.PacienteDto;
import com.sangelp.dh.domain.models.Domicilio;
import com.sangelp.dh.domain.models.Paciente;
import com.sangelp.dh.repository.DomicilioRepository;
import com.sangelp.dh.repository.PacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private ModelMapper mapper;

    public Paciente dto2Model (PacienteDto pacienteDto){
        Paciente paciente = new Paciente();

        paciente.setNombre(pacienteDto.getNombre());
        paciente.setApellido(pacienteDto.getApellido());
        paciente.setDni(pacienteDto.getDNI());
        paciente.setEmail(pacienteDto.getEmail());

        Domicilio domicilio = mapper.map(pacienteDto.getDomicilio(),Domicilio.class);
        Domicilio domicilioSaved = domicilioRepository.save(domicilio);
        paciente.setDomicilio(domicilioSaved);


        return paciente;
    }


}
