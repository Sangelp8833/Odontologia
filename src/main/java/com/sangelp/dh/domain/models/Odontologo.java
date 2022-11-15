package com.sangelp.dh.domain.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "odontologos")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odontologo_id")
    private Long id;

    @NotNull(message = "matricula no bebe estar vacío.")
    private Long matricula;
    @NotNull(message = "nombre no debe estar vacío.")
    private String nombre;
    @NotNull(message = "apellido no debe estar vacío.")
    private String apellido;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "paciente")
    private Set<Turno> turnos;

}
