package com.sangelp.dh.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TurnosAsignadosDto {

    private Long id;
    private PacienteDto paciente;
    private OdontologoDto odontologo;
    private LocalDateTime date;

}
