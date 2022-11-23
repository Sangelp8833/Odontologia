package com.sangelp.dh.domain.Controllers;

import com.sangelp.dh.domain.services.OdonotoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/odontologo")
public class OdontologoController {

    @Autowired
    OdonotoloRepository odonotoloRepository;

    @GetMapping()
    public ResponseEntity<?> listarOdonotologo(){
        return new ResponseEntity<>(odonotoloRepository.findAll(), HttpStatus.OK);
    }

}
