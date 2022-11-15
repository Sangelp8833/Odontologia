package com.sangelp.dh.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PacienteDto {

    @NotBlank(message = "nombre no puede estar vacío.")
    private String nombre;
    @NotBlank(message = "apellido no puede estar vacío.")
    private String apellido;
    @NotBlank(message = "email no puede estar vacío.")
    private String email;
    @NotBlank(message = "DNI no puede estar vacío.")
    private Long DNI;
}
