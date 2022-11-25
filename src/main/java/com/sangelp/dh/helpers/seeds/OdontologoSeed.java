package com.sangelp.dh.helpers.seeds;

import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.services.odontologo.impl.OdontologoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OdontologoSeed implements CommandLineRunner {

    @Autowired
    private OdontologoImpl odontologoImpl;


    @Override
    public void run(String... args) throws Exception {
        createOdontologos();
    }

    public void createOdontologos() {

        OdontologoDto natalia = new OdontologoDto();
        OdontologoDto andres = new OdontologoDto();

        natalia.setNombre("Natalia");
        natalia.setApellido("Usma");
        natalia.setMatricula(12344L);

        andres.setNombre("Andres");
        andres.setApellido("Doe");
        andres.setMatricula(45627L);

        odontologoImpl.saveOdontologo(natalia);
        odontologoImpl.saveOdontologo(andres);
    }

}
