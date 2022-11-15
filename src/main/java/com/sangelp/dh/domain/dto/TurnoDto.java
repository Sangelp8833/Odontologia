package com.sangelp.dh.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TurnoDto {

    @NotBlank(message = "El paciente no puede estar vacío.")
    private PacienteDto pacienteDto;
    @NotBlank(message = "El odontólogo no puede estar vacío.")
    private OdontologoDto odontologoDto;

}
