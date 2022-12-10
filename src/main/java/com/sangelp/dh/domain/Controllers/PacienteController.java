package com.sangelp.dh.domain.Controllers;

import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.dto.PacienteDto;
import com.sangelp.dh.domain.services.paciente.impl.PacienteImpl;
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
@RequestMapping(value = PacienteController.PACIENTE_URL)
@Tag(name = "PacienteRest")
public class PacienteController {

    public static final String PACIENTE_URL =  "/paciente";

    @Autowired
    PacienteImpl pacienteImpl;

    @GetMapping()
    @Operation(summary = "Endpoint para listar los pacientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La petición ha sido exitosa.",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OdontologoDto.class)))
                    })
    })
    public ResponseEntity<?> listarPacientes(){
        if(pacienteImpl.findAll().isEmpty()){
            return new ResponseEntity<>("No hay pacientes registrados en la base de datos.", HttpStatus.NOT_FOUND);
        }else{
            Map<String,Object> message = new HashMap<>();
            message.put("Pacientes",pacienteImpl.findAll());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PostMapping()
    @Operation(summary = "Endpoint para agregar pacientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La petición ha sido exitosa.",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OdontologoDto.class))
                    }),
            @ApiResponse(responseCode = "400",description = "Petición errada.")
    })
    public ResponseEntity<?> registrarPaciente(@RequestBody PacienteDto pacienteDtoDto){
        if(pacienteImpl.findByDni(pacienteDtoDto.getDNI()) == null){
            Map<String,Object> message = new HashMap<>();
            message.put("Paciente",pacienteImpl.savePaciente(pacienteDtoDto));
            return new ResponseEntity<>(message, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Ya hay un paciente registrado en la base de datos con ese DNI.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping()
    @Operation(summary = "Endpoint para actualizar pacientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha actualizado correctamente el paciente."),
            @ApiResponse(responseCode = "404",description = "Petición errada, no se ha encontrado ningún paciente.")
    })
    public ResponseEntity<?> actualizarPaciente(@RequestBody Map<String, Object> partialUpdate,@RequestParam("id") Long pacienteId ){
        if(pacienteImpl.updatePaciente(partialUpdate, pacienteId)){
            return new ResponseEntity<>("El paciente ha sido actualizado correctamente.",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No se ha encontrado el paciente para actualizar.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    @Operation(summary = "Endpoint para eliminar pacientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha eliminado correctamente el paciente."),
            @ApiResponse(responseCode = "404",description = "Petición errada, no se ha encontrado ningún paciente.")
    })
    public ResponseEntity<?>eliminarPaciente(@RequestParam("id") Long pacienteId ){
        if(pacienteImpl.deletePaciente(pacienteId)){
            return new ResponseEntity<>("El paciente ha sido eliminado correctamente.",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("El paciente buscado no se encuentra en la base de datos.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/by-dni")
    @Operation(summary = "Endpoint para buscar paciente por DNI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La petición ha sido exitosa.",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OdontologoDto.class))
                    }),
            @ApiResponse(responseCode = "400",description = "Petición errada.")
    })
    public ResponseEntity<?>buscarPacientePorDni(@RequestParam("Dni") Long dni ){
        if(pacienteImpl.findByDni(dni) != null){
            Map<String,Object> message = new HashMap<>();
            message.put("Paciente",pacienteImpl.findByDni(dni));
            return new ResponseEntity<>(message,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("El paciente buscado no se encuentra en la base de datos.", HttpStatus.NOT_FOUND);
        }
    }


}
