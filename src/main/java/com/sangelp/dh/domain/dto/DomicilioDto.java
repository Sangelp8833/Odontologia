package com.sangelp.dh.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class DomicilioDto {


    private String calle;
    private String numero;
    private String localidad;
    private String provincia;


}
