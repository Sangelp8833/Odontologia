package com.sangelp.dh.domain.Controllers;

import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.services.odontologo.impl.OdontologoImpl;
import com.sangelp.dh.helpers.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = OdontologoController.ODONTOLOGO_URL)
@Tag(name = "OdontólogoRest")
public class OdontologoController {

    public static final String ODONTOLOGO_URL =  "/odontologo";

    @Autowired
    OdontologoImpl odontologoImpl;

    @GetMapping()
    @Operation(summary = "Endpoint para listar los odontólogos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La petición ha sido exitosa.",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OdontologoDto.class)))
            })
    })
    public ResponseEntity<?> listarOdontologo(){
        if(odontologoImpl.findAll().isEmpty()){
            return new ResponseEntity<>("No hay Odontologos registrados en la base de datos.", HttpStatus.NOT_FOUND);
        }else{
            Map<String,Object> message = new HashMap<>();
            message.put("Odontologos",odontologoImpl.findAll());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PostMapping()
    @Operation(summary = "Endpoint para agregar odontólogos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La petición ha sido exitosa.",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OdontologoDto.class))
                    }),
            @ApiResponse(responseCode = "400",description = "Petición errada.")
    })
    public ResponseEntity<?> registrarOdontologo(@RequestBody OdontologoDto odontologoDto){
        if(odontologoImpl.findByMatricula(odontologoDto.getMatricula()) == null){
            Map<String,Object> message = new HashMap<>();
            message.put("Odontologo",odontologoImpl.saveOdontologo(odontologoDto));
            return new ResponseEntity<>(message, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Ya hay un odontólogo registrados en la base de datos con esa Matricula.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping()
    @Operation(summary = "Endpoint para actualizar odontólogos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha actualizado correctamente el odontólogo."),
            @ApiResponse(responseCode = "404",description = "Petición errada, no se ha encontrado ningún odontólogo.")
    })
    public ResponseEntity<?> actualizarOdontologo(@RequestBody Map<String, Object> partialUpdate,@RequestParam("id") Long odontologoId ){
        if(odontologoImpl.updateOdontologo(partialUpdate, odontologoId)){
            return new ResponseEntity<>("El odontólogo ha sido actualizado correctamente.",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No se ha encontrado el odontólogo para actualizar.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    @Operation(summary = "Endpoint para eliminar odontólogos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha eliminado correctamente el odontólogo."),
            @ApiResponse(responseCode = "404",description = "Petición errada, no se ha encontrado ningún odontólogo.")
    })
    public ResponseEntity<?>eliminarOdontologo(@RequestParam("id") Long odontologoId ){
        if(odontologoImpl.deleteOndontologo(odontologoId)){
            return new ResponseEntity<>("El odontólogo ha sido eliminado correctamente",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Error al intentar eliminar el odontólogo, verifique que si existe en la base de datos" +
                    "y que no tenga turnos asignados.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/by-matricula")
    @Operation(summary = "Endpoint para buscar odontólogo por matrícula.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La petición ha sido exitosa.",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OdontologoDto.class))
                    }),
            @ApiResponse(responseCode = "400",description = "Petición errada.")
    })
    public ResponseEntity<?>buscarOdontologoPorMatricula(@RequestParam("matricula") Long matricula ){
        if(odontologoImpl.findByMatricula(matricula) != null){
            Map<String,Object> message = new HashMap<>();
            message.put("odontólogo",odontologoImpl.findByMatricula(matricula));
            return new ResponseEntity<>(message,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("El odontólogo buscado no se encuntra en la base de datos.", HttpStatus.NOT_FOUND);
        }
    }

}
