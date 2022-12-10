package com.sangelp.dh.domain.services.paciente;

import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.dto.PacienteDto;

import java.util.List;
import java.util.Map;

public interface PacienteService {

    PacienteDto savePaciente(PacienteDto pacienteDto);
    List<PacienteDto> findAll();
    PacienteDto findById(Long id);
    PacienteDto findByDni(Long dni);
    boolean deletePaciente(Long id);
    boolean updatePaciente(Map<String,Object> partialUpdate, Long id);
}
