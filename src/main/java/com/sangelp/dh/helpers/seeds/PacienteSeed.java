package com.sangelp.dh.helpers.seeds;

import com.sangelp.dh.domain.dto.DomicilioDto;
import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.dto.PacienteDto;
import com.sangelp.dh.domain.services.paciente.impl.PacienteImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PacienteSeed implements CommandLineRunner {

    @Autowired
    private PacienteImpl pacienteImpl;


    @Override
    public void run(String... args) throws Exception {
        createPaciente();
    }

    public void createPaciente() {

        PacienteDto angela = new PacienteDto();
        PacienteDto carlos = new PacienteDto();

        DomicilioDto domicilioAngela = new DomicilioDto();
        DomicilioDto domicilioCarlos = new DomicilioDto();

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

        pacienteImpl.savePaciente(angela);
        pacienteImpl.savePaciente(carlos);
    }
}
