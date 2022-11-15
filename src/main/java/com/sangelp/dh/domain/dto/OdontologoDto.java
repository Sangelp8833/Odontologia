package com.sangelp.dh.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OdontologoDto {

    @NotBlank(message = "La matricula no puede estar vacío.")
    private Long matricula;
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacío.")
    private String apellido;

}
