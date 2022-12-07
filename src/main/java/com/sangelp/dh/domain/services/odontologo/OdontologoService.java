package com.sangelp.dh.domain.services.odontologo;

import com.sangelp.dh.domain.dto.OdontologoDto;

import java.util.List;
import java.util.Map;

public interface OdontologoService {

    OdontologoDto saveOdontologo(OdontologoDto odontologoDto);
    List<OdontologoDto> findAll();
    OdontologoDto findById(Long id);
    OdontologoDto findByMatricula(Long matricula);
    boolean deleteOndontologo(Long id);
    boolean updateOdontologo(Map<String,Object> partialUpdate, Long id);

}
