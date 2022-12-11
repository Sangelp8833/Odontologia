package com.sangelp.dh.domain.Controllers;

import com.sangelp.dh.domain.dto.DomicilioDto;
import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.dto.PacienteDto;
import com.sangelp.dh.domain.services.paciente.impl.PacienteImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.DomainEvents;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacienteControllerTest {


    PacienteDto angela = new PacienteDto();
    PacienteDto carlos = new PacienteDto();

    DomicilioDto domicilioAngela = new DomicilioDto();
    DomicilioDto domicilioCarlos = new DomicilioDto();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void initMethod() {
        domicilioAngela.setCalle("53");
        domicilioAngela.setNumero("22-01");
        domicilioAngela.setLocalidad("Medellín");
        domicilioAngela.setProvincia("Antioquia");

        domicilioCarlos.setCalle("25");
        domicilioCarlos.setNumero("30-22");
        domicilioCarlos.setLocalidad("Envigado");
        domicilioCarlos.setProvincia("Antioquia");

        angela.setNombre("Angela");
        angela.setApellido("Doe");
        angela.setDNI(12346787L);
        angela.setEmail("angela@gmail.com");
        angela.setDomicilio(domicilioAngela);

        carlos.setNombre("Carlos");
        carlos.setApellido("Doe");
        carlos.setDNI(102873628L);
        carlos.setEmail("carlos@gmail.com");
        carlos.setDomicilio(domicilioCarlos);
    }

    @Mock
    PacienteImpl pacienteImpl;

    @InjectMocks
    PacienteController pacienteController;

    @Test
    @DisplayName("Listar pacientes")
    void listarPacientes() {
        List<PacienteDto> pacienteDtos = new ArrayList<>();
        pacienteDtos.add(angela);
        pacienteDtos.add(carlos);

        Mockito.when(pacienteImpl.findAll()).thenReturn(pacienteDtos);

        ResponseEntity<?> respuesta = pacienteController.listarPacientes();

        Assertions.assertEquals(HttpStatus.OK,respuesta.getStatusCode());
    }

    @Test
    @DisplayName("Resgistrar pacientes")
    void registrarPaciente() {
        Mockito.when(pacienteImpl.findByDni(angela.getDNI())).thenReturn(angela);
        Mockito.when(pacienteImpl.findByDni(carlos.getDNI())).thenReturn(null);

        Mockito.when(pacienteImpl.savePaciente(carlos)).thenReturn(carlos);

        ResponseEntity<?> respuesta_1 = pacienteController.registrarPaciente(angela);
        ResponseEntity<?> respuesta_2 = pacienteController.registrarPaciente(carlos);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,respuesta_1.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK,respuesta_2.getStatusCode());
    }

    @Test
    @DisplayName("Buscar paciente por DNI")
    void buscarPacientePorDni() {
        Mockito.when(pacienteImpl.findByDni(angela.getDNI())).thenReturn(angela);
        Mockito.when(pacienteImpl.findByDni(carlos.getDNI())).thenReturn(null);

        ResponseEntity<?> respuesta_1 = pacienteController.buscarPacientePorDni(angela.getDNI());
        ResponseEntity<?> respuesta_2 = pacienteController.buscarPacientePorDni(carlos.getDNI());

        Assertions.assertEquals(HttpStatus.OK,respuesta_1.getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND,respuesta_2.getStatusCode());
    }
}