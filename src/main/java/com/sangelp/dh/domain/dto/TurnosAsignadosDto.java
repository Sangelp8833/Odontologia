package com.sangelp.dh.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TurnosAsignadosDto {

    private Long id;
    private PacienteDto paciente;
    private OdontologoDto odontologo;

}
