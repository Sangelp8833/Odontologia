package com.sangelp.dh.domain.Controllers;

import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.services.odontologo.impl.OdontologoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoControllerTest {

    OdontologoDto odontologoDto = new OdontologoDto();
    OdontologoDto odontologoDto1 = new OdontologoDto();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void initMethod(){
        odontologoDto.setMatricula(1234567L);
        odontologoDto.setNombre("Carlos");
        odontologoDto.setApellido("Dominguez");

        odontologoDto1.setMatricula(88L);
        odontologoDto1.setNombre("Fulanito");
        odontologoDto1.setApellido("Perez");
    }

    @Mock
    OdontologoImpl odontologoImpl;

    @InjectMocks
    OdontologoController odontologoController;

    @Test
    @DisplayName("Listar Odontólogos")
    void listarOdontologo() {
        List<OdontologoDto> odontologoDtos = new ArrayList<>();
        odontologoDtos.add(odontologoDto1);
        odontologoDtos.add(odontologoDto);

        Mockito.when(odontologoImpl.findAll()).thenReturn(odontologoDtos);

        ResponseEntity<?> respuesta = odontologoController.listarOdontologo();

        Assertions.assertEquals(HttpStatus.OK,respuesta.getStatusCode());

    }

    @Test
    @DisplayName("Registrar Odontólogos")
    void registrarOdontologo() {
        Mockito.when(odontologoImpl.findByMatricula(odontologoDto.getMatricula())).thenReturn(odontologoDto);
        Mockito.when(odontologoImpl.findByMatricula(odontologoDto1.getMatricula())).thenReturn(null);

        Mockito.when(odontologoImpl.saveOdontologo(odontologoDto1)).thenReturn(odontologoDto1);

        ResponseEntity<?> respuesta_1 = odontologoController.registrarOdontologo(odontologoDto);
        ResponseEntity<?> respuesta_2 = odontologoController.registrarOdontologo(odontologoDto1);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,respuesta_1.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK,respuesta_2.getStatusCode());
    }

    @Test
    @DisplayName("Buscar Odontólogos por matrícula")
    void buscarOdontologoPorMatricula() {
        Mockito.when(odontologoImpl.findByMatricula(odontologoDto.getMatricula())).thenReturn(odontologoDto);
        Mockito.when(odontologoImpl.findByMatricula(odontologoDto1.getMatricula())).thenReturn(null);

        ResponseEntity<?> respuesta_1 = odontologoController.buscarOdontologoPorMatricula(odontologoDto.getMatricula());
        ResponseEntity<?> respuesta_2 = odontologoController.buscarOdontologoPorMatricula(odontologoDto1.getMatricula());

        Assertions.assertEquals(HttpStatus.OK,respuesta_1.getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND,respuesta_2.getStatusCode());

    }
}