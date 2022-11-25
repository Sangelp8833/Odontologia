package com.sangelp.dh.domain.Controllers;

import com.sangelp.dh.domain.services.odontologo.impl.OdontologoImpl;
import com.sangelp.dh.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/odontologo")
public class OdontologoController {

    @Autowired
    OdontologoImpl odontologoImpl;

    @GetMapping()
    public ResponseEntity<?> listarOdonotologo(){
        if(odontologoImpl.findAll().isEmpty()){
            return new ResponseEntity<>("No hay Odontologos registrados en la base de datos.", HttpStatus.NOT_FOUND);
        }else{
            Map<String,Object> message = new HashMap<>();
            message.put("Odontologos",odontologoImpl.findAll());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

}
