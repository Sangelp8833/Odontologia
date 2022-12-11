package com.sangelp.dh.helpers.mappers;

import com.sangelp.dh.domain.dto.DomicilioDto;
import com.sangelp.dh.domain.dto.PacienteDto;
import com.sangelp.dh.domain.models.Domicilio;
import com.sangelp.dh.domain.models.Paciente;
import com.sangelp.dh.repository.DomicilioRepository;
import com.sangelp.dh.repository.PacienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PacienteMapperTest {

    DomicilioDto domicilioAngela = new DomicilioDto();
    PacienteDto angela = new PacienteDto();
    Domicilio domicilio = new Domicilio();


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

        angela.setNombre("Angela");
        angela.setApellido("Doe");
        angela.setDNI(12346787L);
        angela.setEmail("angela@gmail.com");
        angela.setDomicilio(domicilioAngela);

        domicilio.setCalle(domicilioAngela.getCalle());
        domicilio.setLocalidad(domicilioAngela.getLocalidad());
        domicilio.setProvincia(domicilioAngela.getProvincia());
        domicilio.setNumero(domicilioAngela.getNumero());
        domicilio.setId(1L);
    }

    @Mock
    private DomicilioRepository domicilioRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private PacienteMapper pacienteMapper;


    @Test
    @DisplayName("Dto To Model")
    void dto2Model() {
        Mockito.when(domicilioRepository.save(domicilio)).thenReturn(domicilio);
        Mockito.when(mapper.map(angela.getDomicilio(),Domicilio.class)).thenReturn(domicilio);

        pacienteMapper.dto2Model(angela);

        Assertions.assertEquals(Paciente.class,pacienteMapper.dto2Model(angela).getClass());
    }
}