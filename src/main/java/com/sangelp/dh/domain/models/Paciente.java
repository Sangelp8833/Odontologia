package com.sangelp.dh.domain.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paciente_id")
    private Long id;

    @NotNull(message = "nombre no debe estar vacío.")
    private String nombre;
    @NotNull(message = "apellido no debe estar vacío.")
    private String apellido;
    @NotNull(message = "email no debe estar vacío.")
    private String email;
    @NotNull(message = "DNI no debe estar vacío.")
    private Long DNI;
    private LocalDateTime fechaIngreso = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @ManyToOne
    @NotNull(message = "El domicilio no debe estar vacío.")
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "odontologo")
    private Set<Turno> turnos;

}
