package com.sangelp.dh.domain.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pacientes")
@SQLDelete(sql = "UPDATE pacientes SET deleted = true WHERE paciente_id = ?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
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
    private Long dni;
    private LocalDateTime fechaIngreso = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @ManyToOne
    @NotNull(message = "El domicilio no debe estar vacío.")
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "paciente")
    private Set<Turno> turnos;

    private boolean deleted = Boolean.FALSE;

}
