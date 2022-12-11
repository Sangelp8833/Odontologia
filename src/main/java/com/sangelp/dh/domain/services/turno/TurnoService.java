package com.sangelp.dh.domain.services.turno;

import com.sangelp.dh.domain.dto.TurnoDto;
import com.sangelp.dh.domain.dto.TurnosAsignadosDto;

import java.text.ParseException;
import java.util.List;

public interface TurnoService {

    TurnosAsignadosDto saveTurno(TurnoDto turnoDto) throws ParseException;
    List<TurnosAsignadosDto> findAll();
    TurnosAsignadosDto findById(Long id);
    boolean deleteTurno(Long id);
}
