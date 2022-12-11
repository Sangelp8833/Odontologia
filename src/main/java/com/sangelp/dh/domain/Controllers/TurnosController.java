package com.sangelp.dh.domain.Controllers;

import com.sangelp.dh.domain.dto.OdontologoDto;
import com.sangelp.dh.domain.dto.TurnoDto;
import com.sangelp.dh.domain.dto.TurnosAsignadosDto;
import com.sangelp.dh.domain.services.odontologo.impl.OdontologoImpl;
import com.sangelp.dh.domain.services.turno.impl.TurnoImpl;
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

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = TurnosController.TURNOS_URL)
@Tag(name = "TurnosRest")
public class TurnosController {

    public static final String TURNOS_URL =  "/turnos";

    @Autowired
    TurnoImpl turnoImpl;

    @GetMapping()
    @Operation(summary = "Endpoint para listar los Turnos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La petición ha sido exitosa.",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TurnoDto.class)))
                    })
    })
    public ResponseEntity<?> listarPacientes(){
        if(turnoImpl.findAll().isEmpty()){
            return new ResponseEntity<>("No hay Turnos registrados en la base de datos.", HttpStatus.NOT_FOUND);
        }else{
            Map<String,Object> message = new HashMap<>();
            message.put("Turnos",turnoImpl.findAll());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PostMapping()
    @Operation(summary = "Endpoint para asignar turnos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La petición ha sido exitosa.",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TurnosAsignadosDto.class))
                    }),
            @ApiResponse(responseCode = "400",description = "Petición errada.")
    })
    public ResponseEntity<?> registrarTurnos(@RequestBody TurnoDto turnoDto) throws ParseException {
        Map<String,Object> message = new HashMap<>();
        message.put("Turno",turnoImpl.saveTurno(turnoDto));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping()
    @Operation(summary = "Endpoint para eliminar Turnos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha eliminado correctamente el turno."),
            @ApiResponse(responseCode = "404",description = "Petición errada, no se ha encontrado ningún turno.")
    })
    public ResponseEntity<?>eliminarTurno(@RequestParam("id") Long turnoId ){
        if(turnoImpl.deleteTurno(turnoId)){
            return new ResponseEntity<>("El turno ha sido cancelado correctamente.",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("El turno buscado no se encuentra en la base de datos.", HttpStatus.NOT_FOUND);
        }
    }

}
