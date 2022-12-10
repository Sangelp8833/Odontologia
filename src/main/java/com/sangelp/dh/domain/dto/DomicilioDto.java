package com.sangelp.dh.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class DomicilioDto {

    @NotBlank(message = "calle no puede estar vacío.")
    private String calle;
    @NotBlank(message = "número no puede estar vacío.")
    private String numero;
    @NotBlank(message = "localidad no puede estar vacío.")
    private String localidad;
    @NotBlank(message = "provincia no puede estar vacío.")
    private String provincia;


}
