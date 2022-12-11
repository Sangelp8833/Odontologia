package com.sangelp.dh.domain.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "domicilios")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domicilio_id")
    private Long id;

    @NotNull(message = "La calle no debe estar vacío.")
    private String calle;

    @NotNull(message = "El número no debe estar vacío.")
    private String numero;

    @NotNull(message = "La localidad no debe estar vacío.")
    private String localidad;

    @NotNull(message = "La provincia no debe estar vacío.")
    private String provincia;

    private String direccionCompleta = "calle "+this.calle+"#"+this.numero+"\n"+this.localidad+"\n"+this.provincia;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "domicilio")
    private Set<Paciente> pacientes;

}
