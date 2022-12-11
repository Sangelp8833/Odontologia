package com.sangelp.dh.domain.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "odontologos")
@SQLDelete(sql = "UPDATE odontologos SET deleted = true WHERE odontologo_id = ?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "odontologo")
    private Set<Turno> turnos;

    private boolean deleted = Boolean.FALSE;

}
