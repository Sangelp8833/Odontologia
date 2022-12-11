package com.sangelp.dh.domain.services.turno;

import com.sangelp.dh.domain.dto.PacienteDto;
import com.sangelp.dh.domain.dto.TurnoDto;
import com.sangelp.dh.domain.dto.TurnosAsignadosDto;

import java.util.List;
import java.util.Map;

public interface TurnoService {

    TurnosAsignadosDto saveTurno(TurnoDto turnoDto);
    List<TurnosAsignadosDto> findAll();
    TurnosAsignadosDto findById(Long id);
    boolean deleteTurno(Long id);
    boolean updateTurno(Map<String,Object> partialUpdate, Long id);
}
