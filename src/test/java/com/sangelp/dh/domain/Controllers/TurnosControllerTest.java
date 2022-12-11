package com.sangelp.dh.domain.Controllers;

import com.sangelp.dh.domain.dto.*;
import com.sangelp.dh.domain.services.turno.impl.TurnoImpl;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurnosControllerTest {

    TurnoDto turno1 = new TurnoDto();
    PacienteDto angela = new PacienteDto();
    DomicilioDto domicilioAngela = new DomicilioDto();
    OdontologoDto odontologoDto = new OdontologoDto();
    TurnosAsignadosDto turnosAsignados1 = new TurnosAsignadosDto();

    @BeforeEach
    public void initMethod() {
        turno1.setOdontologoId(1L);
        turno1.setPacienteId(1L);
        turno1.setDate("05-12-2022");
        turno1.setHour("22:30");

        domicilioAngela.setCalle("53");
        domicilioAngela.setNumero("22-01");
        domicilioAngela.setLocalidad("Medellín");
        domicilioAngela.setProvincia("Antioquia");
        angela.setNombre("Angela");
        angela.setApellido("Doe");
        angela.setDNI(12346787L);
        angela.setEmail("angela@gmail.com");
        angela.setDomicilio(domicilioAngela);

        odontologoDto.setMatricula(1234567L);
        odontologoDto.setNombre("Carlos");
        odontologoDto.setApellido("Dominguez");

        turnosAsignados1.setOdontologo(odontologoDto);
        turnosAsignados1.setPaciente(angela);
        turnosAsignados1.setId(1L);

    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    TurnoImpl turnoImpl;

    @InjectMocks
    TurnosController turnosController;

    @Test
    @DisplayName("Listar turnos")
    void listarTurnos() {
        List<TurnosAsignadosDto> turnosAsignadosDtos = new ArrayList<>();

        Mockito.when(turnoImpl.findAll()).thenReturn(turnosAsignadosDtos);

        ResponseEntity<?> respuesta = turnosController.listarTurnos();

        Assertions.assertEquals(HttpStatus.NOT_FOUND,respuesta.getStatusCode());
    }

    @Test
    @DisplayName("Asignar turnos")
    void registrarTurnos() throws ParseException {
        Mockito.when(turnoImpl.saveTurno(turno1)).thenReturn(turnosAsignados1);

        ResponseEntity<?> respuesta = turnosController.registrarTurnos(turno1);

        Assertions.assertEquals(HttpStatus.OK,respuesta.getStatusCode());
    }
}